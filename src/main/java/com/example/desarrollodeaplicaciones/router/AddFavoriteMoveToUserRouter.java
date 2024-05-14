package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.usecases.AddFavoriteMoveToUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddFavoriteMoveToUserRouter extends UserRouter {

  private final AddFavoriteMoveToUser addFavoriteMoveToUser;

  public AddFavoriteMoveToUserRouter(AddFavoriteMoveToUser addFavoriteMoveToUser) {
    this.addFavoriteMoveToUser = addFavoriteMoveToUser;
  }

  @PostMapping("/{userId}/movies/{movieId}/favorites")
  public ResponseEntity<Void> addFavoriteMoveToUser(
      @PathVariable Long userId, @PathVariable Long movieId) {
    addFavoriteMoveToUser.accept(
        AddFavoriteMoveToUser.Model.builder().userId(userId).movieId(movieId).build());

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
