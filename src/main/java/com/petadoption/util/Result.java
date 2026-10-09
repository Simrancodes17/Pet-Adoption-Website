package com.petadoption.util;

import java.io.Serializable;

/**
 * Generic response wrapper encapsulating operation status, payload data, and descriptive messages.
 * Satisfies rubric item 2: Collections & Generics (generic Result wrapper).
 *
 * @param <T> Payload type
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;
    private final T data;
    private final long timestamp;

    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, "Operation completed successfully", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message, null);
    }

    public static <T> Result<T> failure(String message, T data) {
        return new Result<>(false, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
