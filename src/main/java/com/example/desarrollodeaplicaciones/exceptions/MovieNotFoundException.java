package com.example.desarrollodeaplicaciones.exceptions;

public class MovieNotFoundException extends RuntimeException {

  public MovieNotFoundException(String message) {
    super(message);
  }

  public MovieNotFoundException(Long id) {
    super("Not exists a movie with id: " + id);
  }
}
