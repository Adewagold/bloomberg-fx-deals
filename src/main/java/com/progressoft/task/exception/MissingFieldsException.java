package com.progressoft.task.exception;

public class MissingFieldsException extends BaseException {
    private final String code = "11";
    public MissingFieldsException(String code, String message) {
        super(code, message);
    }

    @Override
    public String getCode() {
        return code;
    }
}
