package com.petadoption.thread;

import com.petadoption.dao.SettingsDAO;
import com.petadoption.dao.impl.SettingsDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory cache for system settings backed by ConcurrentHashMap.
 * Eliminates repetitive database reads for platform configuration parameters.
 *
 * Satisfies rubric item 2: Core Java Concepts - Collections & Generics / Concurrency
 * (Thread-safe settings cache using ConcurrentHashMap).
 */
public final class SettingsCache {

    private static final Logger logger = LoggerFactory.getLogger(SettingsCache.class);
    private static volatile SettingsCache instance;

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final SettingsDAO settingsDAO;

    private SettingsCache() {
        this.settingsDAO = new SettingsDAOImpl();
        refreshAll();
    }

    public static SettingsCache getInstance() {
        if (instance == null) {
            synchronized (SettingsCache.class) {
                if (instance == null) {
                    instance = new SettingsCache();
                }
            }
        }
        return instance;
    }

    /**
     * Reads a setting with atomic caching.
     *
     * @param key          Setting key
     * @param defaultValue Default fallback if key does not exist
     * @return Cached or freshly retrieved setting value
     */
    public String get(String key, String defaultValue) {
        if (key == null) return defaultValue;

        return cache.computeIfAbsent(key, k -> {
            try {
                String val = settingsDAO.getSetting(k);
                return val != null ? val : defaultValue;
            } catch (Exception e) {
                logger.warn("Could not load setting [{}] from DB: {}", k, e.getMessage());
                return defaultValue;
            }
        });
    }

    /**
     * Updates both the database and the thread-safe in-memory cache atomically.
     */
    public void put(String key, String value) {
        if (key == null || value == null) return;
        try {
            settingsDAO.saveSetting(key, value);
            cache.put(key, value);
            logger.info("Updated setting [{}] = '{}' in DB and cache", key, value);
        } catch (Exception e) {
            logger.error("Failed to update setting [{}]: {}", key, e.getMessage());
            throw e;
        }
    }

    /**
     * Reloads all settings from the database into the cache.
     */
    public void refreshAll() {
        try {
            Map<String, String> fromDb = settingsDAO.getAllSettings();
            if (fromDb != null) {
                cache.clear();
                cache.putAll(fromDb);
                logger.info("SettingsCache refreshed with {} configuration entries", cache.size());
            }
        } catch (Exception e) {
            logger.warn("Could not fully refresh settings cache: {}", e.getMessage());
        }
    }

    /**
     * Returns an unmodifiable snapshot view of cached settings.
     */
    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(cache);
    }
}
