package com.petadoption.dao.impl;

import com.petadoption.dao.UserDAO;
import com.petadoption.exception.DatabaseException;
import com.petadoption.model.User;
import com.petadoption.model.UserFactory;
import com.petadoption.util.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of UserDAO.
 * Uses PreparedStatements for all queries and maps ResultSets to polymorphic User instances.
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private final DBConnectionUtil dbUtil;

    public UserDAOImpl() {
        this.dbUtil = DBConnectionUtil.getInstance();
    }

    public UserDAOImpl(DBConnectionUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT id, name, email, password_hash, role, contact_info, created_at FROM users WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by id {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to find user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, name, email, password_hash, role, contact_info, created_at FROM users WHERE email = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by email {}: {}", email, e.getMessage());
            throw new DatabaseException("Failed to find user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, name, email, password_hash, role, contact_info, created_at FROM users ORDER BY id ASC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all users: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve users", e);
        }
        return list;
    }

    @Override
    public List<User> findByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, name, email, password_hash, role, contact_info, created_at FROM users WHERE role = ? ORDER BY id ASC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding users by role {}: {}", role, e.getMessage());
            throw new DatabaseException("Failed to retrieve users by role", e);
        }
        return list;
    }

    @Override
    public int save(User entity) {
        String sql = "INSERT INTO users (name, email, password_hash, role, contact_info) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPasswordHash());
            ps.setString(4, entity.getRole());
            ps.setString(5, entity.getContactInfo());

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
            logger.error("Error saving user {}: {}", entity.getEmail(), e.getMessage());
            throw new DatabaseException("Failed to create user", e);
        }
        return -1;
    }

    @Override
    public boolean update(User entity) {
        String sql = "UPDATE users SET name = ?, email = ?, role = ?, contact_info = ? WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getRole());
            ps.setString(4, entity.getContactInfo());
            ps.setInt(5, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating user {}: {}", entity.getId(), e.getMessage());
            throw new DatabaseException("Failed to update user", e);
        }
    }

    @Override
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating password for user {}: {}", userId, e.getMessage());
            throw new DatabaseException("Failed to update user password", e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting user {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete user", e);
        }
    }

    @Override
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error counting users: {}", e.getMessage());
        }
        return 0;
    }

    @Override
    public int countUsersByRole(String role) {
        String sql = "SELECT COUNT(*) FROM users WHERE role = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Error counting users by role {}: {}", role, e.getMessage());
        }
        return 0;
    }

    /**
     * Maps a ResultSet row to a concrete polymorphic User subclass via UserFactory.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String passwordHash = rs.getString("password_hash");
        String role = rs.getString("role");
        String contactInfo = rs.getString("contact_info");
        Timestamp createdAt = rs.getTimestamp("created_at");

        return UserFactory.createUser(id, name, email, passwordHash, role, contactInfo, createdAt);
    }
}
