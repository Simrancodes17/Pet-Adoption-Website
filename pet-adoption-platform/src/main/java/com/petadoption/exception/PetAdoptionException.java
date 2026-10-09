package com.petadoption.exception;

/**
 * Base checked exception for domain and business rule violations in the Pet Adoption Platform.
 */
public class PetAdoptionException extends Exception {
    private static final long serialVersionUID = 1L;

    public PetAdoptionException(String message) {
        super(message);
    }

    public PetAdoptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
