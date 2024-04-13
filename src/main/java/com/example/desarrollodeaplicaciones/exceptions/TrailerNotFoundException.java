package com.example.desarrollodeaplicaciones.exceptions;

public class TrailerNotFoundException extends RuntimeException {
  public TrailerNotFoundException(Long movieId) {
    super("Trailer from movie with ID " + movieId + " not found");
  }
}
