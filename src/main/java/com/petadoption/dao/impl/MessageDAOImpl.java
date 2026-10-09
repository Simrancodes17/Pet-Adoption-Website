package com.petadoption.dao.impl;

import com.petadoption.dao.MessageDAO;
import com.petadoption.exception.DatabaseException;
import com.petadoption.model.Message;
import com.petadoption.util.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of MessageDAO for user messaging.
 */
public class MessageDAOImpl implements MessageDAO {

    private static final Logger logger = LoggerFactory.getLogger(MessageDAOImpl.class);
    private final DBConnectionUtil dbUtil;

    private static final String BASE_SELECT =
        "SELECT m.*, " +
        "u_snd.name AS sender_name, u_snd.role AS sender_role, " +
        "u_rcv.name AS receiver_name, u_rcv.email AS receiver_email, " +
        "p.name AS pet_name " +
        "FROM messages m " +
        "JOIN users u_snd ON m.sender_id = u_snd.id " +
        "JOIN users u_rcv ON m.receiver_id = u_rcv.id " +
        "LEFT JOIN applications a ON m.application_id = a.id " +
        "LEFT JOIN pets p ON a.pet_id = p.id ";

    public MessageDAOImpl() {
        this.dbUtil = DBConnectionUtil.getInstance();
    }

    public MessageDAOImpl(DBConnectionUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public Optional<Message> findById(int id) {
        String sql = BASE_SELECT + "WHERE m.id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMessage(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding message #{}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to find message", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        List<Message> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY m.id DESC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToMessage(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all messages: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve messages", e);
        }
        return list;
    }

    @Override
    public List<Message> findByUserId(int userId) {
        List<Message> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE m.sender_id = ? OR m.receiver_id = ? ORDER BY m.created_at ASC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMessage(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding messages for user #{}: {}", userId, e.getMessage());
            throw new DatabaseException("Failed to retrieve user messages", e);
        }
        return list;
    }

    @Override
    public List<Message> findConversation(int user1Id, int user2Id, Integer applicationId) {
        List<Message> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT);
        sql.append("WHERE ((m.sender_id = ? AND m.receiver_id = ?) OR (m.sender_id = ? AND m.receiver_id = ?)) ");

        if (applicationId != null && applicationId > 0) {
            sql.append("AND m.application_id = ? ");
        }
        sql.append("ORDER BY m.created_at ASC");

        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, user1Id);
            ps.setInt(2, user2Id);
            ps.setInt(3, user2Id);
            ps.setInt(4, user1Id);

            if (applicationId != null && applicationId > 0) {
                ps.setInt(5, applicationId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMessage(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding conversation: {}", e.getMessage());
            throw new DatabaseException("Failed to retrieve conversation", e);
        }
        return list;
    }

    @Override
    public List<Message> findByApplicationId(int applicationId) {
        List<Message> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE m.application_id = ? ORDER BY m.created_at ASC";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, applicationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMessage(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding messages for app #{}: {}", applicationId, e.getMessage());
            throw new DatabaseException("Failed to retrieve application messages", e);
        }
        return list;
    }

    @Override
    public int save(Message entity) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, application_id, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entity.getSenderId());
            ps.setInt(2, entity.getReceiverId());
            if (entity.getApplicationId() != null) {
                ps.setInt(3, entity.getApplicationId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, entity.getContent());

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
            logger.error("Error saving message: {}", e.getMessage());
            throw new DatabaseException("Failed to send message", e);
        }
        return -1;
    }

    @Override
    public boolean update(Message entity) {
        String sql = "UPDATE messages SET content = ? WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getContent());
            ps.setInt(2, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating message #{}: {}", entity.getId(), e.getMessage());
            throw new DatabaseException("Failed to update message", e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting message #{}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to delete message", e);
        }
    }

    @Override
    public int countMessages() {
        String sql = "SELECT COUNT(*) FROM messages";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Error counting messages: {}", e.getMessage());
        }
        return 0;
    }

    private Message mapResultSetToMessage(ResultSet rs) throws SQLException {
        int appId = rs.getInt("application_id");
        Integer applicationId = rs.wasNull() ? null : appId;

        Message msg = new Message(
            rs.getInt("id"),
            rs.getInt("sender_id"),
            rs.getInt("receiver_id"),
            applicationId,
            rs.getString("content"),
            rs.getTimestamp("created_at")
        );
        try {
            msg.setSenderName(rs.getString("sender_name"));
            msg.setSenderRole(rs.getString("sender_role"));
            msg.setReceiverName(rs.getString("receiver_name"));
            msg.setReceiverEmail(rs.getString("receiver_email"));
            msg.setPetName(rs.getString("pet_name"));
        } catch (SQLException ignored) {
        }
        return msg;
    }
}
