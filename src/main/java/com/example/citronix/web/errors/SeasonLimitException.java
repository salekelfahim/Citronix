package com.example.citronix.web.errors;

public class SeasonLimitException extends RuntimeException{
    public SeasonLimitException(String message){
        super (message);
    }
}
