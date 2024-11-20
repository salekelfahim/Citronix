package com.example.citronix.web.errors;

public class InvalidFieldAreaException extends RuntimeException {
    public InvalidFieldAreaException() {
        super("Field area must be at least 0.1 hectares (1,000 m²).");
    }
}
