package com.petadoption;

import com.petadoption.dao.UserDAO;
import com.petadoption.dao.impl.UserDAOImpl;
import com.petadoption.exception.InvalidCredentialsException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.User;
import com.petadoption.service.UserService;
import com.petadoption.util.AuthTokenUtil;
import com.petadoption.util.DBConnectionUtil;
import com.petadoption.util.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verification of requirements:
 * 1. Single sign-up & duplicate email validation ("Account already exists. Please log in.")
 * 2. Successful login authentication
 * 3. Invalid credentials rejection
 * 4. Cryptographic persistent authentication token issuing & validation (Remember-Me)
 */
class AuthenticationAndSessionPersistenceTest {

    private static DBConnectionUtil dbUtil;
    private static UserDAO userDAO;
    private static UserService userService;

    @BeforeAll
    static void setUp() {
        dbUtil = DBConnectionUtil.getInstance();
        userDAO = new UserDAOImpl(dbUtil);
        userService = new UserService(userDAO);
    }

    @Test
    @DisplayName("Sign up once, save to database, and block duplicates with 'Account already exists. Please log in.'")
    void testSignUpOnceAndDuplicateDetection() throws Exception {
        String uniqueEmail = "persistent.user." + System.currentTimeMillis() + "@example.com";

        // First sign-up must succeed
        Result<User> result = userService.registerUser("Persistent Adopter", uniqueEmail, "password123", "ADOPTER", "Austin, TX");
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertTrue(result.getData().getId() > 0);

        // Verify account exists in database
        Optional<User> inDb = userDAO.findByEmail(uniqueEmail);
        assertTrue(inDb.isPresent());
        assertEquals("Persistent Adopter", inDb.get().getName());

        // Duplicate registration must fail with the required message
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            userService.registerUser("Duplicate Attempt", uniqueEmail, "anotherPassword", "ADOPTER", "Somewhere");
        });
        assertEquals("Account already exists. Please log in.", ex.getMessage());
    }

    @Test
    @DisplayName("Login works after sign-up and rejects wrong credentials")
    void testLoginValidation() throws Exception {
        String email = "login.test." + System.currentTimeMillis() + "@example.com";
        userService.registerUser("Test Login User", email, "correctPassword123", "ADOPTER", "Denver, CO");

        // Successful authentication
        User loggedIn = userService.authenticate(email, "correctPassword123");
        assertNotNull(loggedIn);
        assertEquals(email, loggedIn.getEmail());

        // Invalid credentials failure
        assertThrows(InvalidCredentialsException.class, () -> {
            userService.authenticate(email, "wrongPassword");
        });
    }

    @Test
    @DisplayName("Persistent authentication token generation, validation, and tamper resistance")
    void testPersistentAuthTokenLifecycle() throws Exception {
        String email = "token.test." + System.currentTimeMillis() + "@example.com";
        Result<User> reg = userService.registerUser("Token User", email, "secretPass123", "ADOPTER", "Austin, TX");
        User user = reg.getData();

        // 1. Issue cookie via JDK proxy response
        final Cookie[] capturedCookie = new Cookie[1];
        HttpServletResponse proxyResp = (HttpServletResponse) Proxy.newProxyInstance(
                HttpServletResponse.class.getClassLoader(),
                new Class<?>[]{HttpServletResponse.class},
                (proxy, method, args) -> {
                    if ("addCookie".equals(method.getName())) {
                        capturedCookie[0] = (Cookie) args[0];
                    }
                    return null;
                }
        );

        AuthTokenUtil.issueAuthCookie(proxyResp, user);

        Cookie issuedCookie = capturedCookie[0];
        assertNotNull(issuedCookie, "Remember-me cookie should be issued");
        assertEquals(AuthTokenUtil.REMEMBER_COOKIE_NAME, issuedCookie.getName());
        assertEquals(AuthTokenUtil.COOKIE_MAX_AGE_SECONDS, issuedCookie.getMaxAge());
        assertTrue(issuedCookie.isHttpOnly());

        // 2. Validate cookie via JDK proxy request
        HttpServletRequest proxyReq = (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    if ("getCookies".equals(method.getName())) {
                        return new Cookie[]{issuedCookie};
                    }
                    return null;
                }
        );

        User validatedUser = AuthTokenUtil.validateAuthCookie(proxyReq, userDAO);
        assertNotNull(validatedUser, "Persistent cookie should validate successfully");
        assertEquals(user.getId(), validatedUser.getId());
        assertEquals(email, validatedUser.getEmail());

        // 3. Tampered cookie must be rejected
        Cookie tamperedCookie = new Cookie(AuthTokenUtil.REMEMBER_COOKIE_NAME, issuedCookie.getValue() + "tampered");
        HttpServletRequest tamperedReq = (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    if ("getCookies".equals(method.getName())) {
                        return new Cookie[]{tamperedCookie};
                    }
                    return null;
                }
        );

        User rejectedUser = AuthTokenUtil.validateAuthCookie(tamperedReq, userDAO);
        assertNull(rejectedUser, "Tampered cookie must be rejected");
    }
}
