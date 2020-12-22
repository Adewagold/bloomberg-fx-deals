package com.progressoft.task.exception;

public class InvalidDateException extends RuntimeException {
    private final String code = "08";
    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
