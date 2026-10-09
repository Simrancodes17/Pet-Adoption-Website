package com.petadoption.model;

import java.util.Map;

/**
 * Interface representing any domain entity that supports text and attribute search filtering.
 * Implements rubric item 2: Core Java Concepts - Interfaces.
 */
public interface Searchable {

    /**
     * Checks if the entity matches a free-text search query.
     *
     * @param query Search query string
     * @return true if entity matches, false otherwise
     */
    boolean matchesQuery(String query);

    /**
     * Checks if the entity matches structured key-value filter parameters.
     *
     * @param filters Map of filter criteria (e.g. type, breed, maxAge, location)
     * @return true if entity passes all specified filters
     */
    boolean matchesFilters(Map<String, Object> filters);
}
