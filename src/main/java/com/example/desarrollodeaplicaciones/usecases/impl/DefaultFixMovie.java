package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import com.example.desarrollodeaplicaciones.usecases.FixMovie;
import org.springframework.stereotype.Component;

@Component
public class DefaultFixMovie implements FixMovie {

  private final FixImage<MovieSimpleDto> fixImage;

  public DefaultFixMovie(FixImage<MovieSimpleDto> fixImage) {
    this.fixImage = fixImage;
  }

  @Override
  public void accept(MovieSimpleDto movieSimpleDto) {
    if (isNull(movieSimpleDto.getReleaseDate())) {
      movieSimpleDto.setReleaseDate("");
    }
    if (isNull(movieSimpleDto.getTitle())) {
      movieSimpleDto.setTitle("");
    }
    fixImage.accept(movieSimpleDto);
  }
}
