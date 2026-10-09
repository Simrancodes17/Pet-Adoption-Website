package com.petadoption.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Pet Adopter user looking to adopt companion animals.
 * Inherits from User and overrides polymorphic methods for dashboard routing and permissions.
 */
public class Adopter extends User {
    private static final long serialVersionUID = 1L;

    private static final Set<String> ADOPTER_PERMISSIONS;

    static {
        Set<String> perms = new HashSet<>();
        perms.add("BROWSE_PETS");
        perms.add("APPLY_PET");
        perms.add("VIEW_APPLICATIONS");
        perms.add("CANCEL_APPLICATION");
        perms.add("MESSAGE_SHELTER");
        perms.add("MANAGE_PROFILE");
        ADOPTER_PERMISSIONS = Collections.unmodifiableSet(perms);
    }

    public Adopter() {
        setRole("ADOPTER");
    }

    public Adopter(int id, String name, String email, String passwordHash, String contactInfo, Timestamp createdAt) {
        super(id, name, email, passwordHash, "ADOPTER", contactInfo, createdAt);
    }

    @Override
    public String getDashboardPath() {
        return "/adopter/dashboard";
    }

    @Override
    public Set<String> getPermissions() {
        return ADOPTER_PERMISSIONS;
    }
}
