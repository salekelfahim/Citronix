package com.example.citronix.web.errors;

public class InsufficientHarvestQuantityException extends RuntimeException {
    public InsufficientHarvestQuantityException() {
        super("Not enough quantity in the harvest for this sale.");
    }
}
