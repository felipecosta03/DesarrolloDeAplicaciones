package com.example.desarrollodeaplicaciones.exceptions;

public class MovieNotFoundException extends RuntimeException {

  public MovieNotFoundException() {
    super();
  }

  public MovieNotFoundException(Long id) {
    super("Not exists a movie with id: " + id);
  }
}
