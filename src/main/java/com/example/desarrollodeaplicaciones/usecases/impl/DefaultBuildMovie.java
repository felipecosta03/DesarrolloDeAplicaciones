package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovie;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovie implements BuildMovie {
  @Override
  public MovieSimple apply(MovieSimpleDto movieDetailDto) {
    validateMovieDto(movieDetailDto);
    return MovieSimple.builder()
        .id(movieDetailDto.getId())
        .title(movieDetailDto.getTitle())
        .posterPath(movieDetailDto.getPosterPath())
        .releaseDate(movieDetailDto.getReleaseDate())
        .voteAverage(movieDetailDto.getVoteAverage())
        .popularity(movieDetailDto.getPopularity())
        .genreIds(movieDetailDto.getGenreIds())
        .backdropPath(movieDetailDto.getBackdropPath())
        .voteCount(movieDetailDto.getVoteCount())
        .build();
  }

  private void validateMovieDto(MovieSimpleDto movieDetailDto) {
    if (isNull(movieDetailDto)) {
      throw new BadRequestUseCaseException("movieDetailDto is required");
    }

    if (isNull(movieDetailDto.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
