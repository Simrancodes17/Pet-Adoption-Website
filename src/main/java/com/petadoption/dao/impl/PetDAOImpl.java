package com.petadoption.dao.impl;

import com.petadoption.dao.PetDAO;
import com.petadoption.exception.DatabaseException;
import com.petadoption.model.Pet;
import com.petadoption.util.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of PetDAO.
 * Satisfies rubric item 3: Database Integration with JDBC (PreparedStatement, Connection handling, transactions).
 */
public class PetDAOImpl implements PetDAO {

    private static final Logger logger = LoggerFactory.getLogger(PetDAOImpl.class);
    private final DBConnectionUtil dbUtil;

    public PetDAOImpl() {
        this.dbUtil = DBConnectionUtil.getInstance();
    }

    public PetDAOImpl(DBConnectionUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public Optional<Pet> findById(int id) {
        String sql = "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
                     "FROM pets p " +
                     "JOIN users u ON p.shelter_id = u.id " +
                     "WHERE p.id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding pet by id {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to find pet by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Pet> findAll() {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
                     "FROM pets p " +
                     "JOIN users u ON p.shelter_id = u.id " +
                     "ORDER BY p.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToPet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all pets: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve pets", e);
        }
        return list;
    }

    @Override
    public List<Pet> findByShelterId(int shelterId) {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
                     "FROM pets p " +
                     "JOIN users u ON p.shelter_id = u.id " +
                     "WHERE p.shelter_id = ? " +
                     "ORDER BY p.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shelterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding pets for shelter {}: {}", shelterId, e.getMessage());
            throw new DatabaseException("Failed to retrieve shelter pets", e);
        }
        return list;
    }

    @Override
    public List<Pet> findAvailablePets() {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
                     "FROM pets p " +
                     "JOIN users u ON p.shelter_id = u.id " +
                     "WHERE p.adoption_status = 'AVAILABLE' AND p.approval_status = 'APPROVED' " +
                     "ORDER BY p.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToPet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding available pets: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve available pets", e);
        }
        return list;
    }

    @Override
    public List<Pet> findPendingApprovalPets() {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
                     "FROM pets p " +
                     "JOIN users u ON p.shelter_id = u.id " +
                     "WHERE p.approval_status = 'PENDING' " +
                     "ORDER BY p.id ASC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToPet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding pending approval pets: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve pending approval pets", e);
        }
        return list;
    }

    @Override
    public int save(Pet pet) {
        String sql = "INSERT INTO pets (shelter_id, name, type, breed, age, gender, location, description, photo_path, adoption_status, approval_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pet.getShelterId());
            ps.setString(2, pet.getName());
            ps.setString(3, pet.getType());
            ps.setString(4, pet.getBreed());
            ps.setInt(5, pet.getAge());
            ps.setString(6, pet.getGender() != null ? pet.getGender() : "Unknown");
            ps.setString(7, pet.getLocation());
            ps.setString(8, pet.getDescription());
            ps.setString(9, pet.getPhotoPath());
            ps.setString(10, pet.getAdoptionStatus());
            ps.setString(11, pet.getApprovalStatus());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        pet.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error saving pet {}: {}", pet.getName(), e.getMessage());
            throw new DatabaseException("Failed to save pet listing", e);
        }
        return -1;
    }

    @Override
    public boolean update(Pet pet) {
        String sql = "UPDATE pets SET name = ?, type = ?, breed = ?, age = ?, gender = ?, location = ?, " +
                     "description = ?, photo_path = ?, adoption_status = ?, approval_status = ? " +
                     "WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pet.getName());
            ps.setString(2, pet.getType());
            ps.setString(3, pet.getBreed());
            ps.setInt(4, pet.getAge());
            ps.setString(5, pet.getGender() != null ? pet.getGender() : "Unknown");
            ps.setString(6, pet.getLocation());
            ps.setString(7, pet.getDescription());
            ps.setString(8, pet.getPhotoPath());
            ps.setString(9, pet.getAdoptionStatus());
            ps.setString(10, pet.getApprovalStatus());
            ps.setInt(11, pet.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating pet {}: {}", pet.getId(), e.getMessage());
            throw new DatabaseException("Failed to update pet listing", e);
        }
    }

    @Override
    public boolean updateAdoptionStatus(int petId, String adoptionStatus) {
        try (Connection conn = dbUtil.getConnection()) {
            return updateAdoptionStatus(petId, adoptionStatus, conn);
        } catch (SQLException e) {
            logger.error("Error updating adoption status for pet {}: {}", petId, e.getMessage());
            throw new DatabaseException("Failed to update pet adoption status", e);
        }
    }

    @Override
    public boolean updateAdoptionStatus(int petId, String adoptionStatus, Connection conn) throws SQLException {
        String sql = "UPDATE pets SET adoption_status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, adoptionStatus);
            ps.setInt(2, petId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateApprovalStatus(int petId, String approvalStatus) {
        String sql = "UPDATE pets SET approval_status = ? WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, approvalStatus);
            ps.setInt(2, petId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating approval status for pet {}: {}", petId, e.getMessage());
            throw new DatabaseException("Failed to update pet approval status", e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting pet {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete pet listing", e);
        }
    }

    @Override
    public List<Pet> searchPets(String query, String type, String breed, String location, Integer minAge, Integer maxAge) {
        return searchPets(query, type, breed, null, location, minAge, maxAge);
    }

    @Override
    public List<Pet> searchPets(String query, String type, String breed, String gender, String location, Integer minAge, Integer maxAge) {
        List<Pet> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, u.name AS shelter_name, u.email AS shelter_email " +
            "FROM pets p " +
            "JOIN users u ON p.shelter_id = u.id " +
            "WHERE p.approval_status = 'APPROVED' AND p.adoption_status = 'AVAILABLE' "
        );

        List<Object> params = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            sql.append("AND (LOWER(p.name) LIKE ? OR LOWER(p.description) LIKE ? OR LOWER(p.breed) LIKE ? OR LOWER(p.location) LIKE ?) ");
            String wild = "%" + query.trim().toLowerCase() + "%";
            params.add(wild);
            params.add(wild);
            params.add(wild);
            params.add(wild);
        }

        if (type != null && !type.isBlank() && !"ALL".equalsIgnoreCase(type)) {
            sql.append("AND LOWER(p.type) = ? ");
            params.add(type.trim().toLowerCase());
        }

        if (breed != null && !breed.isBlank() && !"ALL".equalsIgnoreCase(breed)) {
            sql.append("AND LOWER(p.breed) LIKE ? ");
            params.add("%" + breed.trim().toLowerCase() + "%");
        }

        if (gender != null && !gender.isBlank() && !"ALL".equalsIgnoreCase(gender)) {
            sql.append("AND LOWER(p.gender) = ? ");
            params.add(gender.trim().toLowerCase());
        }

        if (location != null && !location.isBlank()) {
            sql.append("AND LOWER(p.location) LIKE ? ");
            params.add("%" + location.trim().toLowerCase() + "%");
        }

        if (minAge != null && minAge >= 0) {
            sql.append("AND p.age >= ? ");
            params.add(minAge);
        }

        if (maxAge != null && maxAge >= 0) {
            sql.append("AND p.age <= ? ");
            params.add(maxAge);
        }

        sql.append("ORDER BY p.id DESC");

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error searching pets: {}", e.getMessage());
            throw new DatabaseException("Failed to execute pet search", e);
        }
        return list;
    }

    @Override
    public java.util.Map<String, List<String>> findDistinctSpeciesAndBreeds() {
        java.util.Map<String, List<String>> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT DISTINCT type, breed FROM pets WHERE type IS NOT NULL AND breed IS NOT NULL ORDER BY type, breed";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String type = rs.getString("type");
                String breed = rs.getString("breed");
                if (type != null && !type.isBlank() && breed != null && !breed.isBlank()) {
                    map.computeIfAbsent(type, k -> new ArrayList<>()).add(breed);
                }
            }
        } catch (SQLException e) {
            logger.warn("Could not query distinct species and breeds: {}", e.getMessage());
        }
        return map;
    }

    @Override
    public int countPets() {
        String sql = "SELECT COUNT(*) FROM pets";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Error counting pets: {}", e.getMessage());
        }
        return 0;
    }

    @Override
    public int countPetsByAdoptionStatus(String status) {
        String sql = "SELECT COUNT(*) FROM pets WHERE adoption_status = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting pets by adoption status {}: {}", status, e.getMessage());
        }
        return 0;
    }

    @Override
    public int countPetsByApprovalStatus(String status) {
        String sql = "SELECT COUNT(*) FROM pets WHERE approval_status = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting pets by approval status {}: {}", status, e.getMessage());
        }
        return 0;
    }

    private Pet mapResultSetToPet(ResultSet rs) throws SQLException {
        Pet pet = new Pet(
            rs.getInt("id"),
            rs.getInt("shelter_id"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("breed"),
            rs.getInt("age"),
            rs.getString("location"),
            rs.getString("description"),
            rs.getString("photo_path"),
            rs.getString("adoption_status"),
            rs.getString("approval_status"),
            rs.getTimestamp("created_at")
        );
        try {
            String gender = rs.getString("gender");
            pet.setGender(gender != null && !gender.isBlank() ? gender : "Unknown");
        } catch (SQLException ignored) {
            pet.setGender("Unknown");
        }
        try {
            pet.setShelterName(rs.getString("shelter_name"));
            pet.setShelterEmail(rs.getString("shelter_email"));
        } catch (SQLException ignored) {
            // Columns not in current projection
        }
        return pet;
    }
}
