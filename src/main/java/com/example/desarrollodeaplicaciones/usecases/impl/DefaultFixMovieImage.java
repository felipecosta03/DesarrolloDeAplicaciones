package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildImageUrl;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fixMovieImage")
public class DefaultFixMovieImage implements FixImage<MovieSimpleDto> {
  private final BuildImageUrl buildImageUrl;

  public DefaultFixMovieImage(BuildImageUrl buildImageUrl) {
    this.buildImageUrl = buildImageUrl;
  }

  @Override
  public void accept(MovieSimpleDto movieSimpleDto) {
    validateMovieSimpleDto(movieSimpleDto);
    if (!isNull(movieSimpleDto.getPosterPath())) {
    movieSimpleDto.setPosterPath(buildImageUrl.apply(movieSimpleDto.getPosterPath()));}
    if (!isNull(movieSimpleDto.getBackdropPath())) {

    movieSimpleDto.setBackdropPath(buildImageUrl.apply(movieSimpleDto.getBackdropPath()));}
  }

  private void validateMovieSimpleDto(MovieSimpleDto movieSimpleDto) {
    if (isNull(movieSimpleDto)) {
      throw new BadRequestUseCaseException("MovieSimpleDto cannot be null");
    }
  }
}
