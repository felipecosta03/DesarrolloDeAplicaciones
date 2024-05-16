package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.usecases.AddFavoriteMovie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddFavoriteMoveRouter extends UserRouter {

  private final AddFavoriteMovie addFavoriteMovie;

  public AddFavoriteMoveRouter(AddFavoriteMovie addFavoriteMovie) {
    this.addFavoriteMovie = addFavoriteMovie;
  }

  @PostMapping("/{userId}/movies/{movieId}/favorites")
  public ResponseEntity<Void> post(@PathVariable Long userId, @PathVariable Long movieId) {
    addFavoriteMovie.accept(
        AddFavoriteMovie.Model.builder().userId(userId).movieId(movieId).build());

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
