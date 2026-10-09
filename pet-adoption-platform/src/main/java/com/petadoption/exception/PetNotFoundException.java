package com.petadoption.exception;

/**
 * Custom checked exception thrown when a requested pet listing cannot be found.
 */
public class PetNotFoundException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    private final int petId;

    public PetNotFoundException(int petId) {
        super("Pet listing with ID #" + petId + " was not found in the system.");
        this.petId = petId;
    }

    public PetNotFoundException(String message) {
        super(message);
        this.petId = -1;
    }

    public int getPetId() {
        return petId;
    }
}
