package com.petadoption.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for secure password hashing and verification using SHA-256 with cryptographic salt.
 * Also provides backward-compatible fallback for seeded default passwords.
 */
public final class PasswordUtil {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH_BYTES = 16;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private PasswordUtil() {
        // Prevent instantiation
    }

    /**
     * Hashes a plain-text password using SHA-256 with a newly generated random salt.
     * Output format: Base64(salt) + "$" + Base64(hash)
     *
     * @param plainPassword Plain-text password to hash
     * @return Salted and hashed password string
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        byte[] salt = new byte[SALT_LENGTH_BYTES];
        SECURE_RANDOM.nextBytes(salt);
        return hashWithSalt(plainPassword, salt);
    }

    /**
     * Hashes a password with a specific salt.
     */
    public static String hashWithSalt(String plainPassword, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(salt);
            byte[] hashBytes = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashBytes);

            return saltBase64 + "$" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password with algorithm: " + HASH_ALGORITHM, e);
        }
    }

    /**
     * Verifies a plain-text password against a stored hashed password.
     * Supports both salted format (salt$hash), plain SHA-256 hex, and fallback demo strings.
     *
     * @param plainPassword Plain-text password entered by user
     * @param storedHash    Stored hash from database
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }

        // Salted hash format: salt$hash
        if (storedHash.contains("$")) {
            String[] parts = storedHash.split("\\$", 2);
            if (parts.length != 2) {
                return false;
            }
            try {
                byte[] salt = Base64.getDecoder().decode(parts[0]);
                String expected = hashWithSalt(plainPassword, salt);
                return expected.equals(storedHash);
            } catch (Exception e) {
                return false;
            }
        }

        // Plain text fallback for ease of demo data initialization
        if (plainPassword.equals(storedHash)) {
            return true;
        }

        // Unsalted SHA-256 fallback
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] bytes = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().equalsIgnoreCase(storedHash);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
