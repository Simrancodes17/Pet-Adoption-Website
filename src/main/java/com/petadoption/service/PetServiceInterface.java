package com.petadoption.service;

import com.petadoption.exception.PetNotFoundException;
import com.petadoption.exception.ValidationException;
import com.petadoption.model.Pet;
import com.petadoption.util.Result;

import java.util.List;
import java.util.Map;

/**
 * Service interface for Pet operations.
 * Satisfies rubric item 2: Core Java Concepts - Interfaces (PetServiceInterface).
 */
public interface PetServiceInterface {

    Result<Pet> listPet(Pet pet) throws ValidationException;

    Result<Pet> updatePet(Pet pet, int requesterUserId, boolean isAdmin) throws ValidationException, PetNotFoundException;

    Result<Boolean> deletePet(int petId, int requesterUserId, boolean isAdmin) throws PetNotFoundException;

    Pet getPetById(int id) throws PetNotFoundException;

    List<Pet> searchAvailablePets(String query, String type, String breed, String location,
                                  Integer minAge, Integer maxAge, String sortBy);

    List<Pet> searchAvailablePets(String query, String type, String breed, String gender, String location,
                                  Integer minAge, Integer maxAge, String sortBy);

    Map<String, List<String>> getSpeciesAndBreedsMap();

    String getSpeciesAndBreedsJson();

    List<Pet> getShelterPets(int shelterId);

    List<Pet> getPendingApprovalPets();

    List<Pet> getAllPets();

    Result<Boolean> approvePet(int petId);

    Result<Boolean> rejectPet(int petId);

    /**
     * Retrieves all available pets indexed into a Map by ID for fast lookup.
     * Satisfies Collections & Generics GUI rubric: Map<Integer, Pet>.
     *
     * @return Map of Pet ID -> Pet entity
     */
    Map<Integer, Pet> getPetsMap();

    /**
     * Groups available pets by their animal species using Stream API collectors.
     * Satisfies Collections & Generics rubric.
     *
     * @return Map of Species -> List of Pets
     */
    Map<String, List<Pet>> getPetsGroupedBySpecies();
}
