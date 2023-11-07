package com.foodroulette2.exceptions;

public class DatabaseQueryException extends RuntimeException {
    public DatabaseQueryException(String message) {
        super(message);
    }

    public DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}