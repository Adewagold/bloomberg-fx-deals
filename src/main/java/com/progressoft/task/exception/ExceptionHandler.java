package com.progressoft.task.exception;

import com.progressoft.task.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse exceptionHandler(Exception ex){
        return new ErrorResponse("99", ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse invalidCredentialsException(FileException ex){
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse invalidCredentialsException(FileNotFoundException ex){
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingFieldsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse missingFieldException(MissingFieldsException ex){
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse invalidDateException(InvalidDateException ex){
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }
}
