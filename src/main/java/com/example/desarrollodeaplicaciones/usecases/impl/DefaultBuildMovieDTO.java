package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDTO;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieDTO implements BuildMovieDTO {
  @Override
  public MovieSimpleDTO apply(MovieSimple movieSimple) {
    validateMovieDetail(movieSimple);
    return MovieSimpleDTO.builder()
        .id(movieSimple.getId())
        .title(movieSimple.getTitle())
        .posterPath(movieSimple.getPosterPath())
        .releaseDate(movieSimple.getReleaseDate())
        .voteAverage(movieSimple.getVoteAverage())
        .popularity(movieSimple.getPopularity())
        .genreIds(movieSimple.getGenreIds())
        .overview(movieSimple.getOverview())
        .build();
  }

  private void validateMovieDetail(MovieSimple movieSimple) {
    if (isNull(movieSimple)) {
      throw new BadRequestUseCaseException("movieDetail is required");
    }

    if (isNull(movieSimple.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
