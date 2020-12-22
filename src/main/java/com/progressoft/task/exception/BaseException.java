package com.progressoft.task.exception;

public class BaseException extends Throwable {
    private String code;
    private String message;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
