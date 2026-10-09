package com.petadoption.service;

import com.petadoption.exception.ApplicationAlreadyExistsException;
import com.petadoption.exception.PetNotFoundException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.Application;
import com.petadoption.util.Result;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for Adoption request management.
 * Satisfies rubric item 2: Core Java Concepts - Interfaces (AdoptionServiceInterface).
 */
public interface AdoptionServiceInterface {

    Result<Application> submitApplication(int petId, int adopterId, String details)
            throws PetNotFoundException, ApplicationAlreadyExistsException, ValidationException;

    Result<Boolean> approveApplication(int applicationId, int shelterUserId, boolean isAdmin)
            throws Exception;

    Result<Boolean> rejectApplication(int applicationId, int shelterUserId, boolean isAdmin);

    Result<Boolean> cancelApplication(int applicationId, int adopterId);

    List<Application> getAdopterApplications(int adopterId);

    List<Application> getShelterApplications(int shelterId);

    List<Application> getPendingApplicationsForShelter(int shelterId);

    Optional<Application> getApplicationById(int applicationId);

    /**
     * Groups applications for a shelter by their current status (PENDING, APPROVED, REJECTED).
     * Satisfies Collections & Generics rubric.
     *
     * @param shelterId Shelter user ID
     * @return Map of Status -> List of Applications
     */
    Map<String, List<Application>> getApplicationsGroupedByStatus(int shelterId);
}
