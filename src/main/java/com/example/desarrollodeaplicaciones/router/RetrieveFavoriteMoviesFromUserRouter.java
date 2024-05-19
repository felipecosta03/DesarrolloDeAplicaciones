package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveFavoriteMoviesFromUserResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveFavoriteMoviesFromUserRouter extends UserRouter {

  private final RetrieveFavoriteMoviesFromUserResponse retrieveFavoriteMoviesFromUserResponse;

  public RetrieveFavoriteMoviesFromUserRouter(
      RetrieveFavoriteMoviesFromUserResponse retrieveFavoriteMoviesFromUserResponse) {
    this.retrieveFavoriteMoviesFromUserResponse = retrieveFavoriteMoviesFromUserResponse;
  }

  @GetMapping("/{userId}/favorites")
  public ResponseEntity<List<MovieSimpleDto>> get(@PathVariable Long userId) {
    Optional<List<MovieSimpleDto>> response =
        retrieveFavoriteMoviesFromUserResponse.apply(
            RetrieveFavoriteMoviesFromUserResponse.Model.builder().userId(userId).build());

    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
