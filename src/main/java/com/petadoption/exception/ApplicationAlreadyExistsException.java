package com.petadoption.exception;

/**
 * Custom checked exception thrown when an adopter attempts to submit duplicate applications
 * for the same pet listing.
 */
public class ApplicationAlreadyExistsException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    private final int petId;
    private final int adopterId;

    public ApplicationAlreadyExistsException(int petId, int adopterId) {
        super(String.format("Adopter #%d already has an active application submitted for Pet #%d.", adopterId, petId));
        this.petId = petId;
        this.adopterId = adopterId;
    }

    public int getPetId() {
        return petId;
    }

    public int getAdopterId() {
        return adopterId;
    }
}
