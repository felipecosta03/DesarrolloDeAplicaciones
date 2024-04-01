package com.example.desarrollodeaplicaciones.exceptions;

public class RateAlreadyExistsException extends RuntimeException {
  public RateAlreadyExistsException(Long movieId, Long userId) {
    super("Rate already exists for movie with ID " + movieId + " from user with ID " + userId);
  }
}
