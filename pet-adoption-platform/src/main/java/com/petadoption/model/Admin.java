package com.petadoption.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an Administrator user with system-wide oversight.
 * Inherits from User and overrides polymorphic methods for dashboard routing and permissions.
 */
public class Admin extends User {
    private static final long serialVersionUID = 1L;

    private static final Set<String> ADMIN_PERMISSIONS;

    static {
        Set<String> perms = new HashSet<>();
        perms.add("MANAGE_USERS");
        perms.add("CREATE_USER");
        perms.add("UPDATE_USER");
        perms.add("DELETE_USER");
        perms.add("APPROVE_PETS");
        perms.add("REJECT_PETS");
        perms.add("MANAGE_SYSTEM_SETTINGS");
        perms.add("VIEW_PLATFORM_ANALYTICS");
        ADMIN_PERMISSIONS = Collections.unmodifiableSet(perms);
    }

    public Admin() {
        setRole("ADMIN");
    }

    public Admin(int id, String name, String email, String passwordHash, String contactInfo, Timestamp createdAt) {
        super(id, name, email, passwordHash, "ADMIN", contactInfo, createdAt);
    }

    @Override
    public String getDashboardPath() {
        return "/admin/dashboard";
    }

    @Override
    public Set<String> getPermissions() {
        return ADMIN_PERMISSIONS;
    }
}
