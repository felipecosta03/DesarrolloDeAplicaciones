package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenre;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMoviesResponse implements RetrieveMoviesResponse {

  private final RetrieveMovies retrieveMovies;
  private final RetrieveMoviesByGenre retrieveMoviesByGenre;
  private final RetrievePopularMovies retrievePopularMovies;
  private final RetrieveMoviesBySearch retrieveMoviesBySearch;

  public DefaultRetrieveMoviesResponse(
      RetrieveMovies retrieveMovies,
      RetrieveMoviesByGenre retrieveMoviesByGenre,
      RetrievePopularMovies retrievePopularMovies,
      RetrieveMoviesBySearch retrieveMoviesBySearch) {
    this.retrieveMovies = retrieveMovies;
    this.retrieveMoviesByGenre = retrieveMoviesByGenre;
    this.retrievePopularMovies = retrievePopularMovies;
    this.retrieveMoviesBySearch = retrieveMoviesBySearch;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    final Integer page = model.getPage().orElse(0);
    final Integer size = model.getSize().orElse(20);
    final String dateOrder = model.getDateOrder().orElse(null);
    final String qualificationOrder = model.getQualificationOrder().orElse(null);

    if (model.getGenre().isPresent() && !model.getGenre().get().isBlank()) {
      log.info("Retrieving movies by genre: {}", model.getGenre().get());
      return retrieveMoviesByGenre.apply(
          RetrieveMoviesByGenre.Model.builder()
              .genre(model.getGenre().get())
              .dateOrder(dateOrder)
              .qualificationOrder(qualificationOrder)
              .page(page)
              .size(size)
              .build());
    } else if (model.getValue().isPresent() && !model.getValue().get().isBlank()) {
      log.info("Retrieving movies by search: {}", model.getValue().get());
      return retrieveMoviesBySearch.apply(
          RetrieveMoviesBySearch.Model.builder()
              .value(model.getValue().get())
              .page(page)
              .size(size)
              .dateOrder(dateOrder)
              .qualificationOrder(qualificationOrder)
              .build());

    } else if (model.getPopularMovies().isPresent() && model.getPopularMovies().get()) {
      log.info("Retrieving popular movies");
      return retrievePopularMovies.apply(
          RetrievePopularMovies.Model.builder().page(page).size(size).build());

    } else {
      log.info("Retrieving movies");
      return retrieveMovies.apply(
          RetrieveMovies.Model.builder()
              .page(page)
              .size(size)
              .dateOrder(dateOrder)
              .qualificationOrder(qualificationOrder)
              .build());
    }
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
