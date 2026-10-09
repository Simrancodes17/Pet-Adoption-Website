package com.petadoption.dao;

import com.petadoption.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User entities.
 */
public interface UserDAO extends DAO<User> {

    /**
     * Finds a user by email address.
     *
     * @param email Email address
     * @return Optional containing the User if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves all users belonging to a specific role.
     *
     * @param role Role identifier ('ADMIN', 'SHELTER', 'ADOPTER')
     * @return List of matching users
     */
    List<User> findByRole(String role);

    /**
     * Updates a user's password hash.
     *
     * @param userId          User ID
     * @param newPasswordHash Hashed password
     * @return true if updated
     */
    boolean updatePassword(int userId, String newPasswordHash);

    /**
     * Counts total registered users.
     */
    int countUsers();

    /**
     * Counts users with a specific role.
     */
    int countUsersByRole(String role);
}
