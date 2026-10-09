package com.petadoption.model;

/**
 * Represents a registered user in the Pet Adoption Platform.
 * Supports roles: Admin, Shelter, Adopter
 */
public record User(
    String id,
    String name,
    String email,
    String role,
    String status,
    String createdDate
) {}
