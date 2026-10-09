package com.petadoption.exception;

import java.util.Collections;
import java.util.List;

/**
 * Custom checked exception thrown when form inputs or business entity constraints fail validation.
 */
public class ValidationException extends PetAdoptionException {
    private static final long serialVersionUID = 1L;

    private final List<String> errorMessages;

    public ValidationException(String message) {
        super(message);
        this.errorMessages = Collections.singletonList(message);
    }

    public ValidationException(List<String> errorMessages) {
        super(String.join(", ", errorMessages));
        this.errorMessages = (errorMessages != null) ? errorMessages : Collections.emptyList();
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
