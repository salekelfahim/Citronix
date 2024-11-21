package com.example.citronix.web.errors;

public class NonProductiveTreeException extends RuntimeException {
    public NonProductiveTreeException() {
        super("Tree is no longer productive as it is older than 20 years.");
    }
}