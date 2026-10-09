package com.petadoption.exception;

/**
 * Custom checked exception thrown when authentication fails due to incorrect email or password.
 */
public class InvalidCredentialsException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
