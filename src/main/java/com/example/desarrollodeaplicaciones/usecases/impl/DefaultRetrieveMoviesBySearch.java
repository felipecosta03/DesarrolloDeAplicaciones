package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesComparator;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitle;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMoviesBySearch implements RetrieveMoviesBySearch {

  private final RetrieveMoviesByTitle retrieveMoviesByTitle;
  private final BuildMoviesComparator buildMoviesComparator;

  public DefaultRetrieveMoviesBySearch(
      RetrieveMoviesByTitle retrieveMoviesByTitle, BuildMoviesComparator buildMoviesComparator) {
    this.retrieveMoviesByTitle = retrieveMoviesByTitle;
    this.buildMoviesComparator = buildMoviesComparator;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    // Retrieve movies by actors from the API or database if an api error occurs

    // Retrieve movies by a search from the API or database if an api error occurs
    log.info("Retrieving movies by title value: {}", model.getValue());
    if (model.getPage() > 11) {
      return Optional.empty();
    }
    final Optional<List<MovieSimpleDto>> moviesByTitle =
        retrieveMoviesByTitle.apply(
            RetrieveMoviesByTitle.Model.builder()
                .title(model.getValue())
                .size(model.getSize())
                .page(model.getPage())
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());

    Comparator<MovieSimpleDto> comparator =
        buildMoviesComparator.apply(
            BuildMoviesComparator.Model.builder()
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());

    // Merge movies of actors with the movies retrieved by the search
    if (!Strings.isNullOrEmpty(model.getQualificationOrder()))
      cleanDate(moviesByTitle.orElse(Collections.emptyList()));
    if (!isNull(comparator))
      moviesByTitle.ifPresent(movieSimpleDtos -> movieSimpleDtos.sort(comparator));

    if (moviesByTitle.isEmpty()) {
      return Optional.empty();
    }
    int finalIndex = Math.min(20, moviesByTitle.get().size());
    return Optional.of(moviesByTitle.get().subList(0, finalIndex));
  }

  private void cleanDate(List<MovieSimpleDto> movies) {
    movies.forEach(
        movie -> {
          if (!Strings.isNullOrEmpty(movie.getReleaseDate())) {
            movie.setReleaseDate(movie.getReleaseDate().substring(0, 4));
          }
          log.info(movie.getReleaseDate());
        });
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
