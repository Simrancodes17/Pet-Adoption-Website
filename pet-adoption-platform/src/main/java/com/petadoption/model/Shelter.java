package com.petadoption.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an Animal Shelter or Rescue Organization user.
 * Inherits from User and overrides polymorphic methods for dashboard routing and permissions.
 */
public class Shelter extends User {
    private static final long serialVersionUID = 1L;

    private static final Set<String> SHELTER_PERMISSIONS;

    static {
        Set<String> perms = new HashSet<>();
        perms.add("LIST_PETS");
        perms.add("UPDATE_PET");
        perms.add("DELETE_PET");
        perms.add("VIEW_SHELTER_PETS");
        perms.add("REVIEW_APPLICATIONS");
        perms.add("APPROVE_APPLICATION");
        perms.add("REJECT_APPLICATION");
        perms.add("MESSAGE_ADOPTER");
        perms.add("VIEW_SHELTER_ANALYTICS");
        SHELTER_PERMISSIONS = Collections.unmodifiableSet(perms);
    }

    public Shelter() {
        setRole("SHELTER");
    }

    public Shelter(int id, String name, String email, String passwordHash, String contactInfo, Timestamp createdAt) {
        super(id, name, email, passwordHash, "SHELTER", contactInfo, createdAt);
    }

    @Override
    public String getDashboardPath() {
        return "/shelter/dashboard";
    }

    @Override
    public Set<String> getPermissions() {
        return SHELTER_PERMISSIONS;
    }
}
