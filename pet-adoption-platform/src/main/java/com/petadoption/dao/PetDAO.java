package com.petadoption.dao;

import com.petadoption.model.Pet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Pet listings.
 */
public interface PetDAO extends DAO<Pet> {

    List<Pet> findByShelterId(int shelterId);

    List<Pet> findAvailablePets();

    List<Pet> findPendingApprovalPets();

    boolean updateAdoptionStatus(int petId, String adoptionStatus);

    boolean updateAdoptionStatus(int petId, String adoptionStatus, Connection conn) throws SQLException;

    boolean updateApprovalStatus(int petId, String approvalStatus);

    List<Pet> searchPets(String query, String type, String breed, String location, Integer minAge, Integer maxAge);

    int countPets();

    int countPetsByAdoptionStatus(String status);

    int countPetsByApprovalStatus(String status);
}
