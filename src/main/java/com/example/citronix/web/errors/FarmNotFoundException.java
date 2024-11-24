package com.example.citronix.web.errors;

public class FarmNotFoundException extends RuntimeException {
    public FarmNotFoundException() {
        super("The specified farm was not found.");
    }

    public FarmNotFoundException(Long farmId) {
        super("Farm not found with id: " + farmId);
    }
}
