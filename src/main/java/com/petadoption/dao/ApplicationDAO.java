package com.petadoption.dao;

import com.petadoption.model.Application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Adoption Applications.
 */
public interface ApplicationDAO extends DAO<Application> {

    List<Application> findByPetId(int petId);

    List<Application> findByAdopterId(int adopterId);

    List<Application> findByShelterId(int shelterId);

    Optional<Application> findByPetAndAdopter(int petId, int adopterId);

    boolean updateStatus(int applicationId, String status);

    boolean updateStatus(int applicationId, String status, Connection conn) throws SQLException;

    /**
     * Atomically rejects all other competing applications for the same pet
     * as part of the adoption approval transaction.
     */
    boolean rejectOtherApplicationsForPet(int petId, int approvedAppId, Connection conn) throws SQLException;

    int countApplications();

    int countApplicationsByStatus(String status);
}
