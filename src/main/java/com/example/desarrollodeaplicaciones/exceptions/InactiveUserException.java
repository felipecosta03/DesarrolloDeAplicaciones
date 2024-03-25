package com.example.desarrollodeaplicaciones.exceptions;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException() {
        super("User is not active");
    }
}
