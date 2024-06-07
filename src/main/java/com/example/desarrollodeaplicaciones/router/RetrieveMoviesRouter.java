package com.example.desarrollodeaplicaciones.router;

import com.azure.core.annotation.QueryParam;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RetrieveMoviesRouter extends MovieRouter {

  private final RetrieveMoviesResponse retrieveMoviesResponse;

  public RetrieveMoviesRouter(RetrieveMoviesResponse retrieveMoviesResponse) {
    this.retrieveMoviesResponse = retrieveMoviesResponse;
  }

  @GetMapping
  public ResponseEntity<List<MovieSimpleDto>> get(
      @QueryParam("date_order") Optional<String> dateOrder,
      @QueryParam("genre") Optional<String> genre,
      @QueryParam("qualification_order") Optional<String> qualificationOrder,
      @QueryParam("popular_movies") Optional<Boolean> popularMovies,
      @QueryParam("value") Optional<String> value,
      @QueryParam("page") Optional<Integer> page,
      @QueryParam("size") Optional<Integer> size) {

    return retrieveMoviesResponse
        .apply(
            RetrieveMoviesResponse.Model.builder()
                .dateOrder(dateOrder)
                .genre(genre)
                .qualificationOrder(qualificationOrder)
                .popularMovies(popularMovies)
                .value(value)
                .page(page)
                .size(size)
                .build())
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.ok(new ArrayList<>()));
  }
}
