package com.example.desarrollodeaplicaciones.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long id) {
    super("Not exists a user with id: " + id);
  }
}
