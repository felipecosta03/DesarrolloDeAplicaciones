package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.Vote;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenre;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesDetailsByIds;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMoviesResponse implements RetrieveMoviesResponse {

  private final RetrieveMovies retrieveMovies;
  private final RetrieveMoviesByGenre retrieveMoviesByGenre;
  private final RetrievePopularMovies retrievePopularMovies;
  private final RetrieveMoviesBySearch retrieveMoviesBySearch;
  private final RetrieveMoviesDetailsByIds retrieveMoviesDetailsByIds;

  public DefaultRetrieveMoviesResponse(
      RetrieveMovies retrieveMovies,
      RetrieveMoviesByGenre retrieveMoviesByGenre,
      RetrievePopularMovies retrievePopularMovies,
      RetrieveMoviesBySearch retrieveMoviesBySearch,
      RetrieveMoviesDetailsByIds retrieveMoviesDetailsByIds) {
    this.retrieveMovies = retrieveMovies;
    this.retrieveMoviesByGenre = retrieveMoviesByGenre;
    this.retrievePopularMovies = retrievePopularMovies;
    this.retrieveMoviesBySearch = retrieveMoviesBySearch;
    this.retrieveMoviesDetailsByIds = retrieveMoviesDetailsByIds;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    final Integer page = model.getPage().orElse(1);
    final Integer size = model.getSize().orElse(20);
    final String dateOrder = model.getDateOrder().orElse(null);
    final String qualificationOrder = model.getQualificationOrder().orElse(null);
    Optional<List<MovieSimpleDto>> movies;
    if (model.getGenre().isPresent() && !model.getGenre().get().isBlank()) {
      log.info("Retrieving movies by genre: {}", model.getGenre().get());
      movies =
          retrieveMoviesByGenre.apply(
              RetrieveMoviesByGenre.Model.builder()
                  .genre(model.getGenre().get())
                  .dateOrder(dateOrder)
                  .qualificationOrder(qualificationOrder)
                  .page(page)
                  .size(size)
                  .build());
    } else if (model.getValue().isPresent() && !model.getValue().get().isBlank()) {
      log.info("Retrieving movies by search: {}", model.getValue().get());
      movies =
          retrieveMoviesBySearch.apply(
              RetrieveMoviesBySearch.Model.builder()
                  .value(model.getValue().get())
                  .page(page)
                  .size(size)
                  .dateOrder(dateOrder)
                  .qualificationOrder(qualificationOrder)
                  .build());

    } else if (model.getPopularMovies().isPresent() && model.getPopularMovies().get()) {
      log.info("Retrieving popular movies");
      movies =
          retrievePopularMovies.apply(
              RetrievePopularMovies.Model.builder().page(page).size(size).build());

    } else {
      log.info("Retrieving movies");
      movies =
          retrieveMovies.apply(
              RetrieveMovies.Model.builder()
                  .page(page)
                  .size(size)
                  .dateOrder(dateOrder)
                  .qualificationOrder(qualificationOrder)
                  .build());
    }
    List<MovieSimpleDto> moviesDto = new ArrayList<>();
    if (movies.isPresent()) {

      moviesDto =
          movies.get().stream()
              .filter(movie -> movie.getId() != null || !Strings.isNullOrEmpty(movie.getTitle()))
              .collect(Collectors.toList());

      Optional<List<MovieDetail>> movieDetails =
          retrieveMoviesDetailsByIds.apply(
              moviesDto.stream().map(MovieSimpleDto::getId).collect(Collectors.toList()));

      if (movieDetails.isPresent()) {
        for (MovieSimpleDto movieSimpleDto : moviesDto) {
          for (MovieDetail movieDetail : movieDetails.get()) {
            if (movieSimpleDto.getId().equals(movieDetail.getId())) {
              movieSimpleDto.setVoteCount(
                  movieDetail.getVotes().size() + movieDetail.getVoteCount());
              movieSimpleDto.setVoteAverage(calculateVoteAverage(movieDetail));
            }
          }
        }
      }
    }
    return Optional.of(moviesDto);
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }

  private Double calculateVoteAverage(MovieDetail movieDetail) {
    return ((movieDetail.getVoteAverage() * movieDetail.getVoteCount())
            + movieDetail.getVotes().stream().mapToDouble(Vote::getScore).sum())
        / (movieDetail.getVoteCount() + movieDetail.getVotes().size());
  }
}
