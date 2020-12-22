package com.progressoft.task.exception;

public class InvalidFieldException extends RuntimeException {
    private final String code = "11";
    public InvalidFieldException(String code, Throwable message) {
        super(code, message);
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
