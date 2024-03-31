package com.example.desarrollodeaplicaciones.exceptions;

public class RateNotFoundException extends RuntimeException {
    public RateNotFoundException() {
        super("Rate not found");
    }
}
