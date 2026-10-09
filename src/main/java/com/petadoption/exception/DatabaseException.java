package com.petadoption.exception;

/**
 * Custom unchecked runtime exception wrapping underlying JDBC/SQL failures,
 * connection timeouts, and transaction rollback issues.
 */
public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
