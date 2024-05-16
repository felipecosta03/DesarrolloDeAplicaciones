package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDto;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMoviesDto implements BuildMoviesDto {

  private final BuildMovieDto buildMovieDto;

  public DefaultBuildMoviesDto(BuildMovieDto buildMovieDto) {
    this.buildMovieDto = buildMovieDto;
  }

  @Override
  public List<MovieSimpleDto> apply(List<MovieSimple> movieSimples) {
    validateModel(movieSimples);
    return movieSimples.stream().map(buildMovieDto).toList();
  }

  private void validateModel(List<MovieSimple> movies) {
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
