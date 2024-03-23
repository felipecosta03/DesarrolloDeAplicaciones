package com.example.desarrollodeaplicaciones.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRegistrationException extends RuntimeException{
    private final List<String> messages;

    public UserRegistrationException() {
        messages = new ArrayList<>();
    }
}
