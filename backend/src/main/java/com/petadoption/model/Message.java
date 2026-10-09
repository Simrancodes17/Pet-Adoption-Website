package com.petadoption.model;

/**
 * In-app chat message between Shelter and Adopter.
 */
public record Message(
    String id,
    String sender, // "shelter" or "adopter"
    String applicantName,
    String text,
    String time
) {}
