package com.example.citronix.web.errors;

public class ExceededFieldCountException extends RuntimeException {
    public ExceededFieldCountException() {
        super("A farm cannot contain more than 10 fields.");
    }
}