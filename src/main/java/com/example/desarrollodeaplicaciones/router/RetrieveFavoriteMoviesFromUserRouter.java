package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveFavoriteMoviesFromUserResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveFavoriteMoviesFromUserRouter extends FavoritesRouter {

  private final RetrieveFavoriteMoviesFromUserResponse retrieveFavoriteMoviesFromUserResponse;

  public RetrieveFavoriteMoviesFromUserRouter(
      RetrieveFavoriteMoviesFromUserResponse retrieveFavoriteMoviesFromUserResponse) {
    this.retrieveFavoriteMoviesFromUserResponse = retrieveFavoriteMoviesFromUserResponse;
  }

  @GetMapping
  public ResponseEntity<List<MovieSimpleDto>> get() {
    Optional<List<MovieSimpleDto>> response =
        retrieveFavoriteMoviesFromUserResponse.apply(
            RetrieveFavoriteMoviesFromUserResponse.Model.builder().userId(getUserId()).build());

    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
