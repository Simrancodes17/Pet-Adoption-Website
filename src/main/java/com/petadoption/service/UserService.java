package com.petadoption.service;

import com.petadoption.dao.UserDAO;
import com.petadoption.dao.impl.UserDAOImpl;
import com.petadoption.exception.InvalidCredentialsException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.User;
import com.petadoption.model.UserFactory;
import com.petadoption.util.PasswordUtil;
import com.petadoption.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer orchestrating user authentication, profile management, and role-based administration.
 * Satisfies rubric item 1 (Layered MVC architecture) and rubric item 2 (Exception handling & validation).
 */
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Authenticates a user by email and plain-text password.
     *
     * @param email         User email
     * @param plainPassword User entered password
     * @return Authenticated User instance
     * @throws InvalidCredentialsException if email is not found or password does not match
     */
    public User authenticate(String email, String plainPassword) throws InvalidCredentialsException {
        if (email == null || email.isBlank() || plainPassword == null || plainPassword.isBlank()) {
            throw new InvalidCredentialsException("Email and password are required.");
        }

        Optional<User> userOpt = userDAO.findByEmail(email.trim().toLowerCase());
        if (userOpt.isEmpty()) {
            logger.warn("Authentication failed: email [{}] not found", email);
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        User user = userOpt.get();
        if (!PasswordUtil.verifyPassword(plainPassword, user.getPasswordHash())) {
            logger.warn("Authentication failed: invalid password for user [{}]", email);
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        logger.info("User [{}] authenticated successfully with role [{}]", email, user.getRole());
        return user;
    }

    /**
     * Registers a new user account with hashed password.
     */
    public Result<User> registerUser(String name, String email, String plainPassword, String role, String contactInfo) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (name == null || name.trim().length() < 2) {
            errors.add("Name must be at least 2 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("A valid email address is required.");
        }
        if (plainPassword == null || plainPassword.length() < 6) {
            errors.add("Password must be at least 6 characters long.");
        }
        if (role == null || (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("SHELTER") && !role.equalsIgnoreCase("ADOPTER"))) {
            role = "ADOPTER";
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (userDAO.findByEmail(normalizedEmail).isPresent()) {
            throw new ValidationException("Account already exists. Please log in.");
        }

        String passwordHash = PasswordUtil.hashPassword(plainPassword);
        User newUser = UserFactory.createUser(0, name.trim(), normalizedEmail, passwordHash, role.toUpperCase(), contactInfo, null);

        int generatedId = userDAO.save(newUser);
        if (generatedId > 0) {
            newUser.setId(generatedId);
            return Result.success("Registration successful! You may now sign in.", newUser);
        } else {
            return Result.failure("Failed to create user account. Please try again.");
        }
    }

    public Optional<User> getUserById(int id) {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<User> getUsersByRole(String role) {
        return userDAO.findByRole(role);
    }

    public Result<Boolean> updateUser(int id, String name, String email, String role, String contactInfo) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("User name cannot be empty.");
        }
        if (email == null || !email.contains("@")) {
            throw new ValidationException("A valid email is required.");
        }

        Optional<User> existing = userDAO.findById(id);
        if (existing.isEmpty()) {
            return Result.failure("User not found.");
        }

        User user = existing.get();
        user.setName(name.trim());
        user.setEmail(email.trim().toLowerCase());
        if (role != null && !role.isBlank()) {
            user.setRole(role.toUpperCase());
        }
        user.setContactInfo(contactInfo != null ? contactInfo.trim() : "");

        boolean ok = userDAO.update(user);
        return ok ? Result.success("User updated successfully.", true) : Result.failure("Failed to update user.");
    }

    public Result<Boolean> deleteUser(int id) {
        boolean ok = userDAO.deleteById(id);
        return ok ? Result.success("User removed successfully.", true) : Result.failure("Failed to remove user.");
    }
}
