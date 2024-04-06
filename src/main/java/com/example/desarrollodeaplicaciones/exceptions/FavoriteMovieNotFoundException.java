package com.example.desarrollodeaplicaciones.exceptions;

public class FavoriteMovieNotFoundException extends RuntimeException {
  public FavoriteMovieNotFoundException(Long movieId, Long userId) {
    super("Favorite movie with ID " + movieId + " not found for user with ID " + userId);
  }
}
