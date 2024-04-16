package com.example.desarrollodeaplicaciones.exceptions;

public class UserImageNotExists extends RuntimeException {
  public UserImageNotExists() {
    super("User image does not exist");
  }
}
