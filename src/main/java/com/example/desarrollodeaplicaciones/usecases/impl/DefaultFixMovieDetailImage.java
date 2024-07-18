package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildImageUrl;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("FixMovieDetailImage")
public class DefaultFixMovieDetailImage implements FixImage<MovieDetailDto> {
  private final BuildImageUrl buildImageUrl;

  public DefaultFixMovieDetailImage(BuildImageUrl buildImageUrl) {
    this.buildImageUrl = buildImageUrl;
  }

  @Override
  public void accept(MovieDetailDto movieDetailDto) {
    validateMovieDetailDto(movieDetailDto);
    movieDetailDto.setPosterPath(buildImageUrl.apply(movieDetailDto.getPosterPath()));

    movieDetailDto.setBackdropPath(buildImageUrl.apply(movieDetailDto.getBackdropPath()));

    movieDetailDto
        .getImages()
        .forEach(image -> image.setFilePath(buildImageUrl.apply(image.getFilePath())));

    movieDetailDto
        .getDirector()
        .setProfilePath(buildImageUrl.apply(movieDetailDto.getDirector().getProfilePath()));

    movieDetailDto
        .getCast()
        .forEach(actor -> actor.setProfilePath(buildImageUrl.apply(actor.getProfilePath())));
  }

  private void validateMovieDetailDto(MovieDetailDto movieDetailDto) {
    if (isNull(movieDetailDto)) {
      throw new BadRequestUseCaseException("MovieDetailDto cannot be null");
    }
    if (isNull(movieDetailDto.getId())) {
      throw new BadRequestUseCaseException("MovieDetailDto id cannot be null");
    }
  }
}
