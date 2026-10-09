package com.petadoption.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract base class representing a user in the Pet Adoption Platform.
 * Demonstrates OOP concepts: Abstraction, Encapsulation, and Polymorphism.
 * Specific user types (Admin, Shelter, Adopter) inherit from this class and
 * provide specialized implementations for permissions and dashboard navigation.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String role; // 'ADMIN', 'SHELTER', 'ADOPTER'
    private String contactInfo;
    private Timestamp createdAt;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int id, String name, String email, String passwordHash, String role, String contactInfo, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.contactInfo = contactInfo;
        this.createdAt = createdAt;
    }

    /**
     * Polymorphic method: Returns the dedicated dashboard URL path for this user role.
     *
     * @return Dashboard route (e.g. "/admin/dashboard", "/shelter/dashboard", "/adopter/dashboard")
     */
    public abstract String getDashboardPath();

    /**
     * Polymorphic method: Returns the set of permissions granted to this user role.
     *
     * @return Unmodifiable Set of permission strings
     */
    public abstract Set<String> getPermissions();

    /**
     * Checks if this user holds a specific permission.
     *
     * @param permission Permission identifier
     * @return true if authorized, false otherwise
     */
    public boolean hasPermission(String permission) {
        Set<String> permissions = getPermissions();
        return permissions != null && permissions.contains(permission);
    }

    // Encapsulation: Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
