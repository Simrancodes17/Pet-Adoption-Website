package com.petadoption.model;

/**
 * Adoption Application model submitted by potential adopters to shelters.
 */
public record AdoptionApplication(
    String id,
    String petId,
    String petName,
    String petBreed,
    String applicantName,
    String applicantEmail,
    String shelterId,
    String housingType,
    String otherPets,
    String reason,
    String status, // "Pending", "Under Review", "Approved", "Rejected", "Completed"
    String submittedDate
) {}
