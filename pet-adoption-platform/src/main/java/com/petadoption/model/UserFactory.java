package com.petadoption.model;

import java.sql.Timestamp;

/**
 * Factory class for instantiating polymorphic User subclasses based on role type.
 */
public final class UserFactory {

    private UserFactory() {
    }

    public static User createUser(int id, String name, String email, String passwordHash, String role, String contactInfo, Timestamp createdAt) {
        if (role == null) {
            role = "ADOPTER";
        }
        String normalizedRole = role.trim().toUpperCase();
        switch (normalizedRole) {
            case "ADMIN":
                return new Admin(id, name, email, passwordHash, contactInfo, createdAt);
            case "SHELTER":
                return new Shelter(id, name, email, passwordHash, contactInfo, createdAt);
            case "ADOPTER":
            default:
                return new Adopter(id, name, email, passwordHash, contactInfo, createdAt);
        }
    }
}
