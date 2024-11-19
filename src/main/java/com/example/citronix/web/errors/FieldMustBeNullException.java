package com.example.citronix.web.errors;

public class FieldMustBeNullException extends RuntimeException{
    public FieldMustBeNullException() {
        super("A farm cannot have fields at the time of creation.");
    }
}

