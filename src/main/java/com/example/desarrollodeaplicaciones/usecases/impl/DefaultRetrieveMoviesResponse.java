package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildMovie;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenre;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesResponse implements RetrieveMoviesResponse {

  private BuildMovieDto buildMovieDTO;
  private BuildMovie buildMovie;
  private RetrieveMovies retrieveMovies;
  private RetrieveMoviesByGenre retrieveMoviesByGenre;
  private RetrievePopularMovies retrievePopularMovies;
  private RetrieveMoviesBySearch retrieveMoviesBySearch;

  public DefaultRetrieveMoviesResponse(
      BuildMovieDto buildMovieDTO,
      BuildMovie buildMovie,
      RetrieveMoviesByGenre retrieveMoviesByGenre,
      RetrievePopularMovies retrievePopularMovies,
      RetrieveMoviesBySearch retrieveMoviesBySearch) {
    this.buildMovieDTO = buildMovieDTO;
    this.buildMovie = buildMovie;
    this.retrieveMoviesByGenre = retrieveMoviesByGenre;
    this.retrievePopularMovies = retrievePopularMovies;
    this.retrieveMoviesBySearch = retrieveMoviesBySearch;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);

    final Integer page = model.getPage().orElse(1);
    final Integer size = model.getSize().orElse(20);
    final String dateOrder = model.getDateOrder().orElse(null);
    final String qualificationOrder = model.getQualificationOrder().orElse(null);

    if (model.getGenre().isPresent() && !model.getGenre().get().isBlank()) {

      return retrieveMoviesByGenre.apply(
          RetrieveMoviesByGenre.Model.builder()
              .genre(model.getGenre().get())
              .dateOrder(dateOrder)
              .qualificationOrder(qualificationOrder)
              .page(page)
              .size(size)
              .build());
    } else if (model.getValue().isPresent() && !model.getValue().get().isBlank()) {

      return retrieveMoviesBySearch.apply(
          RetrieveMoviesBySearch.Model.builder()
              .value(model.getValue().get())
              .page(page)
              .size(size)
              .build());

    } else if (model.getPopularMovies().isPresent() && model.getPopularMovies().get()) {

      return retrievePopularMovies.apply(
          RetrievePopularMovies.Model.builder().page(page).size(size).build());

    } else {

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
