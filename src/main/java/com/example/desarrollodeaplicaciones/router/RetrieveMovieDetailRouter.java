package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.entrypoints.RetrieveMovieDetailEndpoint;
import com.example.desarrollodeaplicaciones.models.ResponseDynamic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Router for retrieving movie details. */
@RestController
public class RetrieveMovieDetailRouter {

  private final RetrieveMovieDetailEndpoint endpoint;

  public RetrieveMovieDetailRouter(RetrieveMovieDetailEndpoint endpoint) {
    this.endpoint = endpoint;
  }

  @GetMapping("/movies/{movieId}")
  private ResponseEntity<ResponseDynamic<MovieDetailDTO>> get(@PathVariable final Long movieId) {
    return ResponseEntity.status(200)
        .body(endpoint.apply(RetrieveMovieDetailEndpoint.Model.builder().movieId(movieId).build()));
  }
}
