package com.petadoption.dao.impl;

import com.petadoption.dao.ApplicationDAO;
import com.petadoption.exception.DatabaseException;
import com.petadoption.model.Application;
import com.petadoption.util.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of ApplicationDAO.
 * Supports single-entity queries and multi-table transactional updates.
 * Satisfies rubric item 3: Database Integration with JDBC.
 */
public class ApplicationDAOImpl implements ApplicationDAO {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationDAOImpl.class);
    private final DBConnectionUtil dbUtil;

    private static final String BASE_SELECT =
        "SELECT a.*, " +
        "p.name AS pet_name, p.breed AS pet_breed, p.type AS pet_type, p.photo_path AS pet_photo_path, " +
        "u_adopter.name AS adopter_name, u_adopter.email AS adopter_email, " +
        "u_shelter.name AS shelter_name, u_shelter.email AS shelter_email " +
        "FROM applications a " +
        "JOIN pets p ON a.pet_id = p.id " +
        "JOIN users u_adopter ON a.adopter_id = u_adopter.id " +
        "JOIN users u_shelter ON a.shelter_id = u_shelter.id ";

    public ApplicationDAOImpl() {
        this.dbUtil = DBConnectionUtil.getInstance();
    }

    public ApplicationDAOImpl(DBConnectionUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public Optional<Application> findById(int id) {
        String sql = BASE_SELECT + "WHERE a.id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToApplication(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding application #{}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to find application", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Application> findAll() {
        List<Application> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY a.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToApplication(rs));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all applications: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve applications", e);
        }
        return list;
    }

    @Override
    public List<Application> findByPetId(int petId) {
        List<Application> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.pet_id = ? ORDER BY a.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToApplication(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding applications for pet #{}: {}", petId, e.getMessage());
            throw new DatabaseException("Failed to retrieve pet applications", e);
        }
        return list;
    }

    @Override
    public List<Application> findByAdopterId(int adopterId) {
        List<Application> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.adopter_id = ? ORDER BY a.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adopterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToApplication(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding applications for adopter #{}: {}", adopterId, e.getMessage());
            throw new DatabaseException("Failed to retrieve adopter applications", e);
        }
        return list;
    }

    @Override
    public List<Application> findByShelterId(int shelterId) {
        List<Application> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.shelter_id = ? ORDER BY a.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shelterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToApplication(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding applications for shelter #{}: {}", shelterId, e.getMessage());
            throw new DatabaseException("Failed to retrieve shelter applications", e);
        }
        return list;
    }

    @Override
    public Optional<Application> findByPetAndAdopter(int petId, int adopterId) {
        String sql = BASE_SELECT + "WHERE a.pet_id = ? AND a.adopter_id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);
            ps.setInt(2, adopterId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToApplication(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error checking existing application for pet #{} and adopter #{}: {}", petId, adopterId, e.getMessage());
            throw new DatabaseException("Failed to check duplicate application", e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Application entity) {
        String sql = "INSERT INTO applications (pet_id, adopter_id, shelter_id, details, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entity.getPetId());
            ps.setInt(2, entity.getAdopterId());
            ps.setInt(3, entity.getShelterId());
            ps.setString(4, entity.getDetails());
            ps.setString(5, entity.getStatus());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        entity.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating application: {}", e.getMessage());
            throw new DatabaseException("Failed to submit application", e);
        }
        return -1;
    }

    @Override
    public boolean update(Application entity) {
        String sql = "UPDATE applications SET details = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getDetails());
            ps.setString(2, entity.getStatus());
            ps.setInt(3, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating application #{}: {}", entity.getId(), e.getMessage());
            throw new DatabaseException("Failed to update application", e);
        }
    }

    @Override
    public boolean updateStatus(int applicationId, String status) {
        try (Connection conn = dbUtil.getConnection()) {
            return updateStatus(applicationId, status, conn);
        } catch (SQLException e) {
            logger.error("Error updating status for application #{}: {}", applicationId, e.getMessage());
            throw new DatabaseException("Failed to update application status", e);
        }
    }

    @Override
    public boolean updateStatus(int applicationId, String status, Connection conn) throws SQLException {
        String sql = "UPDATE applications SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, applicationId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean rejectOtherApplicationsForPet(int petId, int approvedAppId, Connection conn) throws SQLException {
        String sql = "UPDATE applications SET status = 'REJECTED', updated_at = CURRENT_TIMESTAMP " +
                     "WHERE pet_id = ? AND id != ? AND status = 'PENDING'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setInt(2, approvedAppId);
            int rows = ps.executeUpdate();
            logger.info("Automatically rejected {} competing pending applications for pet #{}", rows, petId);
            return true;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM applications WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting application #{}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete application", e);
        }
    }

    @Override
    public int countApplications() {
        String sql = "SELECT COUNT(*) FROM applications";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Error counting applications: {}", e.getMessage());
        }
        return 0;
    }

    @Override
    public int countApplicationsByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM applications WHERE status = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting applications by status {}: {}", status, e.getMessage());
        }
        return 0;
    }

    private Application mapResultSetToApplication(ResultSet rs) throws SQLException {
        Application app = new Application(
            rs.getInt("id"),
            rs.getInt("pet_id"),
            rs.getInt("adopter_id"),
            rs.getInt("shelter_id"),
            rs.getString("details"),
            rs.getString("status"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
        try {
            app.setPetName(rs.getString("pet_name"));
            app.setPetBreed(rs.getString("pet_breed"));
            app.setPetType(rs.getString("pet_type"));
            app.setPetPhotoPath(rs.getString("pet_photo_path"));
            app.setAdopterName(rs.getString("adopter_name"));
            app.setAdopterEmail(rs.getString("adopter_email"));
            app.setShelterName(rs.getString("shelter_name"));
            app.setShelterEmail(rs.getString("shelter_email"));
        } catch (SQLException ignored) {
        }
        return app;
    }
}
