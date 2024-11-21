package com.example.citronix.web.errors;

public class InvalidPlantingPeriodException extends RuntimeException {
    public InvalidPlantingPeriodException() {
        super("Trees can only be planted between March and May.");
    }
}