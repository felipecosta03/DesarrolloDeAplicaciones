package com.example.desarrollodeaplicaciones.exceptions;

public class FavoriteMovieAlreadyExistsException extends RuntimeException {
  public FavoriteMovieAlreadyExistsException(Long movieId, Long userId) {
    super("Favorite movie with ID " + movieId + " already exists for user with ID " + userId);
  }
}
