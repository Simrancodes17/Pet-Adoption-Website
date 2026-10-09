package com.petadoption.dao;

import java.util.Map;

/**
 * Data Access Object interface for system configuration settings.
 */
public interface SettingsDAO {

    String getSetting(String key);

    Map<String, String> getAllSettings();

    boolean updateSetting(String key, String value);

    boolean saveSetting(String key, String value);
}
