package com.example.desarrollodeaplicaciones.router;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.router.BadRequestRouterException;
import com.example.desarrollodeaplicaciones.exceptions.router.NotFoundRouterException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Router for retrieving movie details. */
@RestController
@Slf4j
public class RetrieveMovieDetailRouter extends MovieRouter {

  private final RetrieveMovieDetailResponse retrieveMovieDetailResponse;

  public RetrieveMovieDetailRouter(RetrieveMovieDetailResponse retrieveMovieDetailResponse) {
    this.retrieveMovieDetailResponse = retrieveMovieDetailResponse;
  }

  @GetMapping("/{movieId}")
  public ResponseEntity<MovieDetailDto> get(@PathVariable final Long movieId) {
    if (isNull(movieId)) {
      throw new BadRequestRouterException("Movie ID is required");
    }
    ResponseEntity<MovieDetailDto> response =
        retrieveMovieDetailResponse
            .apply(RetrieveMovieDetailResponse.Model.builder().movieId(movieId).build())
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new NotFoundRouterException("Movie not found"));
    log.info("Movie detail retrieved with id: {}", movieId);

    return response;
  }
}
