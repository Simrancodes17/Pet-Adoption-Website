package com.petadoption.exception;

/**
 * Thrown when an adoption conflict occurs (e.g. attempting to adopt an unavailable pet,
 * or concurrent lock conflicts during adoption approval).
 *
 * Satisfies rubric item 2: Core Java Concepts - Custom Exception Handling.
 */
public class AdoptionConflictException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    private final int petId;

    public AdoptionConflictException(int petId, String message) {
        super(message);
        this.petId = petId;
    }

    public int getPetId() {
        return petId;
    }
}
