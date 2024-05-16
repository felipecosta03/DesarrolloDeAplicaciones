package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.MergeMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitle;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesFromActors;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesBySearch implements RetrieveMoviesBySearch {

  private final RetrieveMoviesByTitle retrieveMoviesByTitle;
  private final RetrieveMoviesFromActors retrieveMoviesFromActors;
  private final MergeMovies mergeMovies;

  public DefaultRetrieveMoviesBySearch(
      RetrieveMoviesByTitle retrieveMoviesByTitle,
      RetrieveMoviesFromActors retrieveMoviesFromActors,
      MergeMovies mergeMovies) {
    this.retrieveMoviesByTitle = retrieveMoviesByTitle;
    this.retrieveMoviesFromActors = retrieveMoviesFromActors;
    this.mergeMovies = mergeMovies;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    // Retrieve movies by a search from the API or database if an api error occurs
    final Optional<List<MovieSimpleDto>> moviesByTitle =
        retrieveMoviesByTitle.apply(
            RetrieveMoviesByTitle.Model.builder()
                .title(model.getValue())
                .size(model.getSize())
                .page(model.getPage())
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());
    // Retrieve movies by actors from the API or database if an api error occurs
    final Optional<List<MovieSimpleDto>> moviesFromActors =
        retrieveMoviesFromActors.apply(
            RetrieveMoviesFromActors.Model.builder()
                .actorName(model.getValue())
                .size(model.getSize())
                .page(model.getPage())
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());
    // Merge movies of actors with the movies retrieved by the search

    return mergeMovies.apply(
        moviesByTitle.orElse(Collections.emptyList()),
        moviesFromActors.orElse(Collections.emptyList()));
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getValue().isBlank()) {
      throw new BadRequestUseCaseException("Value is required");
    }
  }
}
