package com.petadoption.dao.impl;

import com.petadoption.dao.SettingsDAO;
import com.petadoption.exception.DatabaseException;
import com.petadoption.util.DBConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC implementation of SettingsDAO.
 */
public class SettingsDAOImpl implements SettingsDAO {

    private static final Logger logger = LoggerFactory.getLogger(SettingsDAOImpl.class);
    private final DBConnectionUtil dbUtil;

    public SettingsDAOImpl() {
        this.dbUtil = DBConnectionUtil.getInstance();
    }

    public SettingsDAOImpl(DBConnectionUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @Override
    public String getSetting(String key) {
        String sql = "SELECT setting_value FROM settings WHERE setting_key = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("setting_value");
                }
            }
        } catch (SQLException e) {
            logger.error("Error reading setting {}: {}", key, e.getMessage());
            throw new DatabaseException("Failed to read setting", e);
        }
        return null;
    }

    @Override
    public Map<String, String> getAllSettings() {
        Map<String, String> settings = new HashMap<>();
        String sql = "SELECT setting_key, setting_value FROM settings";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                settings.put(rs.getString("setting_key"), rs.getString("setting_value"));
            }
        } catch (SQLException e) {
            logger.error("Error reading all settings: {}", e.getMessage());
            throw new DatabaseException("Failed to read settings", e);
        }
        return settings;
    }

    @Override
    public boolean updateSetting(String key, String value) {
        String sql = "UPDATE settings SET setting_value = ? WHERE setting_key = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            ps.setString(2, key);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return true;
            }
            // If not existing, insert it
            return saveSetting(key, value);
        } catch (SQLException e) {
            logger.error("Error updating setting {}: {}", key, e.getMessage());
            throw new DatabaseException("Failed to update setting", e);
        }
    }

    @Override
    public boolean saveSetting(String key, String value) {
        String sql = "INSERT INTO settings (setting_key, setting_value) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value)";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, key);
            ps.setString(2, value);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // For H2 compatibility where ON DUPLICATE KEY syntax might differ:
            try (Connection conn2 = dbUtil.getConnection();
                 PreparedStatement ps2 = conn2.prepareStatement("MERGE INTO settings (setting_key, setting_value) KEY(setting_key) VALUES (?, ?)")) {
                ps2.setString(1, key);
                ps2.setString(2, value);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                logger.error("Error saving setting {}: {}", key, ex.getMessage());
                throw new DatabaseException("Failed to save setting", ex);
            }
        }
    }
}
