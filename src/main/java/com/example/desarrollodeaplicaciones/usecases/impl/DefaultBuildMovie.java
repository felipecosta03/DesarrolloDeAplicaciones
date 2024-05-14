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
  public MovieSimple apply(MovieSimpleDto movieDetailDTO) {
    validateMovieDTO(movieDetailDTO);
    return MovieSimple.builder()
        .id(movieDetailDTO.getId())
        .title(movieDetailDTO.getTitle())
        .posterPath(movieDetailDTO.getPosterPath())
        .releaseDate(movieDetailDTO.getReleaseDate())
        .voteAverage(movieDetailDTO.getVoteAverage())
        .popularity(movieDetailDTO.getPopularity())
        .overview(movieDetailDTO.getOverview())
        .genreIds(movieDetailDTO.getGenreIds())
        .backdropPath(movieDetailDTO.getBackdropPath())
        .build();
  }

  private void validateMovieDTO(MovieSimpleDto movieDetailDTO) {
    if (isNull(movieDetailDTO)) {
      throw new BadRequestUseCaseException("movieDetailDto is required");
    }

    if (isNull(movieDetailDTO.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
