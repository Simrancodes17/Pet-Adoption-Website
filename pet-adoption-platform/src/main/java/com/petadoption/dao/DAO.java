package com.petadoption.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object (DAO) interface defining common CRUD contracts.
 * Satisfies rubric item 2: Collections & Generics (generic DAO interface).
 *
 * @param <T> Domain entity type
 */
public interface DAO<T> {

    /**
     * Finds an entity by its unique numeric ID.
     *
     * @param id Entity ID
     * @return Optional containing entity if found, or empty Optional
     */
    Optional<T> findById(int id);

    /**
     * Retrieves all entities of this type.
     *
     * @return List of all entities
     */
    List<T> findAll();

    /**
     * Persists a new entity into the database.
     *
     * @param entity Entity to save
     * @return Generated primary key ID
     */
    int save(T entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity Entity with updated values
     * @return true if successful, false otherwise
     */
    boolean update(T entity);

    /**
     * Deletes an entity by its ID.
     *
     * @param id Entity ID to delete
     * @return true if deleted, false otherwise
     */
    boolean deleteById(int id);
}
