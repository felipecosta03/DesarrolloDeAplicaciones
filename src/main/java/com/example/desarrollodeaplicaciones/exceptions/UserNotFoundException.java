package com.example.desarrollodeaplicaciones.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String id) {
    super("Not exists a user with id: " + id);
  }
}
