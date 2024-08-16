package com.tinqinacademy.email.api.operations.exceptions;

public abstract class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
