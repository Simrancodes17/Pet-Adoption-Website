package com.petadoption.service;

import com.petadoption.dao.SettingsDAO;
import com.petadoption.dao.impl.SettingsDAOImpl;
import com.petadoption.thread.SettingsCache;
import com.petadoption.util.Result;

import java.util.Map;

/**
 * Service managing platform configuration settings with ConcurrentHashMap cache backing.
 */
public class SettingsService {

    private final SettingsDAO settingsDAO;
    private final SettingsCache settingsCache;

    public SettingsService() {
        this.settingsDAO = new SettingsDAOImpl();
        this.settingsCache = SettingsCache.getInstance();
    }

    public SettingsService(SettingsDAO settingsDAO, SettingsCache settingsCache) {
        this.settingsDAO = settingsDAO;
        this.settingsCache = settingsCache;
    }

    public String getSetting(String key, String defaultValue) {
        return settingsCache.get(key, defaultValue);
    }

    public Map<String, String> getAllSettings() {
        return settingsDAO.getAllSettings();
    }

    public Result<Boolean> updateSetting(String key, String value) {
        if (key == null || key.isBlank()) {
            return Result.failure("Setting key cannot be empty.");
        }
        settingsCache.put(key.trim(), value != null ? value.trim() : "");
        return Result.success("Setting '" + key + "' updated successfully.", true);
    }
}
