package com.example.citronix.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFieldAreaException.class)
    public String handleInvalidFieldAreaException(InvalidFieldAreaException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceededFieldAreaException.class)
    public String handleExceededFieldAreaException(ExceededFieldAreaException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceededTotalFieldAreaException.class)
    public String handleExceededTotalFieldAreaException(ExceededTotalFieldAreaException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceededFieldCountException.class)
    public String handleExceededFieldCountException(ExceededFieldCountException e) {
        return e.getMessage();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FarmNotFoundException.class)
    public String handleFarmNotFoundException(FarmNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FieldMustBeNullException.class)
    public String handleFieldMustBeNullException(FieldMustBeNullException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGenericException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected error occurred");
        error.put("message", e.getMessage());
        return error;
    }
}
