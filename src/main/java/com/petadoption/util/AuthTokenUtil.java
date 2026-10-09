package com.petadoption.util;

import com.petadoption.dao.UserDAO;
import com.petadoption.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

/**
 * Utility for issuing, validating, and clearing secure persistent authentication tokens (Remember-Me cookies).
 * Uses HMAC-SHA256 signature combining user ID, expiration timestamp, server secret, and user password hash.
 */
public final class AuthTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenUtil.class);

    public static final String REMEMBER_COOKIE_NAME = "paw_remember_token";
    public static final int COOKIE_MAX_AGE_SECONDS = 30 * 24 * 60 * 60; // 30 days
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SERVER_SECRET = "pawhaven-secure-auth-secret-key-2026-production-token";

    private AuthTokenUtil() {
    }

    /**
     * Issues a persistent authentication cookie to the client.
     */
    public static void issueAuthCookie(HttpServletResponse resp, User user) {
        if (resp == null || user == null) {
            return;
        }

        long expiryTime = System.currentTimeMillis() + ((long) COOKIE_MAX_AGE_SECONDS * 1000L);
        String rawData = user.getId() + ":" + expiryTime;
        String signature = generateSignature(user.getId(), expiryTime, user.getPasswordHash());
        String tokenPayload = rawData + ":" + signature;

        String encodedToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenPayload.getBytes(StandardCharsets.UTF_8));

        Cookie cookie = new Cookie(REMEMBER_COOKIE_NAME, encodedToken);
        cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // Enabled in HTTPS production
        resp.addCookie(cookie);

        logger.debug("Issued persistent remember-me cookie for user #{}", user.getId());
    }

    /**
     * Validates the remember-me cookie from incoming request and returns the authenticated User if valid.
     */
    public static User validateAuthCookie(HttpServletRequest req, UserDAO userDAO) {
        if (req == null || req.getCookies() == null || userDAO == null) {
            return null;
        }

        for (Cookie cookie : req.getCookies()) {
            if (REMEMBER_COOKIE_NAME.equals(cookie.getName())) {
                String token = cookie.getValue();
                if (token == null || token.isBlank()) {
                    continue;
                }

                try {
                    String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
                    String[] parts = decoded.split(":", 3);
                    if (parts.length != 3) {
                        continue;
                    }

                    int userId = Integer.parseInt(parts[0]);
                    long expiryTime = Long.parseLong(parts[1]);
                    String providedSignature = parts[2];

                    if (System.currentTimeMillis() > expiryTime) {
                        logger.info("Persistent auth token for user #{} has expired", userId);
                        continue;
                    }

                    Optional<User> userOpt = userDAO.findById(userId);
                    if (userOpt.isEmpty()) {
                        logger.warn("Persistent auth token references non-existent user #{}", userId);
                        continue;
                    }

                    User user = userOpt.get();
                    String expectedSignature = generateSignature(userId, expiryTime, user.getPasswordHash());

                    if (expectedSignature.equals(providedSignature)) {
                        logger.info("Successfully re-authenticated user [{}] from persistent auth cookie", user.getEmail());
                        return user;
                    } else {
                        logger.warn("Invalid signature on persistent auth token for user #{}", userId);
                    }
                } catch (Exception e) {
                    logger.debug("Failed parsing remember-me cookie: {}", e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Clears the persistent authentication cookie upon explicit logout.
     */
    public static void clearAuthCookie(HttpServletResponse resp) {
        if (resp == null) {
            return;
        }
        Cookie cookie = new Cookie(REMEMBER_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);
        logger.debug("Cleared persistent remember-me cookie");
    }

    private static String generateSignature(int userId, long expiryTime, String passwordHash) {
        try {
            String payload = userId + ":" + expiryTime + ":" + (passwordHash != null ? passwordHash : "");
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(SERVER_SECRET.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(secretKey);
            byte[] rawHmac = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error calculating HMAC signature for auth token", e);
        }
    }
}
