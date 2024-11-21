package com.example.citronix.web.errors;

public class ExceededTreeDensityException extends RuntimeException {
    public ExceededTreeDensityException() {
        super("Field exceeds the maximum allowed tree density.");
    }
}
