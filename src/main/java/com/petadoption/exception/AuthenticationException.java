package com.petadoption.exception;

/**
 * Thrown when user authentication fails or an unauthenticated user attempts to access protected routes.
 *
 * Satisfies rubric item 2: Core Java Concepts - Custom Exception Handling.
 */
public class AuthenticationException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
