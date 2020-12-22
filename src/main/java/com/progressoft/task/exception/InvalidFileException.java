package com.progressoft.task.exception;

public class InvalidFileException extends BaseException{
    private final String code = "10";
    public InvalidFileException(String code, String message) {
        super(code, message);
    }

    @Override
    public String getCode() {
        return code;
    }
}
