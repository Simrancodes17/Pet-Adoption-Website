package com.petadoption.model;

/**
 * Pet model representing animals listed for adoption.
 */
public record Pet(
    String id,
    String name,
    String type,
    String breed,
    String age,
    String gender,
    String location,
    String shelterId,
    String shelterName,
    String image,
    String description,
    String vaccinations,
    String goodWith,
    String approvalStatus, // "Pending", "Approved", "Rejected"
    String adoptionStatus, // "Available", "Pending", "Adopted"
    String submittedDate
) {}
