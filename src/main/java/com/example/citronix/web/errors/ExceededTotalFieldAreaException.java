package com.example.citronix.web.errors;

public class ExceededTotalFieldAreaException extends RuntimeException {
    public ExceededTotalFieldAreaException() {
        super("Total field area exceeds the farm's total area.");
    }
}