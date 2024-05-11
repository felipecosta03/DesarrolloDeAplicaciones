package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovie;
import com.example.desarrollodeaplicaciones.usecases.BuildMovies;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovies implements BuildMovies {

  private final BuildMovie buildMovie;

  public DefaultBuildMovies(BuildMovie buildMovie) {
    this.buildMovie = buildMovie;
  }

  @Override
  public List<MovieSimple> apply(List<MovieSimpleDto> movies) {
    validateModel(movies);
    return movies.stream().map(buildMovie).collect(Collectors.toList());
  }

  private void validateModel(List<MovieSimpleDto> movies) {
    if (isNull(movies)) {
      throw new BadRequestUseCaseException("Movie list is required");
    }
    if (movies.isEmpty()) {
      throw new BadRequestUseCaseException("Movie list is empty");
    }
    if (movies.stream().anyMatch(Objects::isNull)) {
      throw new BadRequestUseCaseException("Movie list contains null values");
    }
  }
}
