package com.example.citronix.web.errors;

public class ExceededFieldAreaException extends RuntimeException {
    public ExceededFieldAreaException() {
        super("Field area cannot exceed 50% of the farm's total area.");
    }
}
