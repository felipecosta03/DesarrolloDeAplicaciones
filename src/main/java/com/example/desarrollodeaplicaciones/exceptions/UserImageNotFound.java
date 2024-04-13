package com.example.desarrollodeaplicaciones.exceptions;

public class UserImageNotFound extends RuntimeException {
  public UserImageNotFound() {
    super("User image not found");
  }
}
