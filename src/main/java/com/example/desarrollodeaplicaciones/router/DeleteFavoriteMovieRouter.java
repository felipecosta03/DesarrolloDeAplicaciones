package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.usecases.DeleteFavoriteMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteFavoriteMovieRouter extends UserRouter {

  private final DeleteFavoriteMovie deleteFavoriteMovie;

  public DeleteFavoriteMovieRouter(DeleteFavoriteMovie deleteFavoriteMovie) {
    this.deleteFavoriteMovie = deleteFavoriteMovie;
  }

  @DeleteMapping("/movies/{movieId}/favorites")
  public ResponseEntity<Void> delete(@PathVariable("movieId") Long movieId) {
    deleteFavoriteMovie.accept(
        DeleteFavoriteMovie.Model.builder().userId(getUserId()).movieId(movieId).build());
    return ResponseEntity.ok().build();
  }
}
