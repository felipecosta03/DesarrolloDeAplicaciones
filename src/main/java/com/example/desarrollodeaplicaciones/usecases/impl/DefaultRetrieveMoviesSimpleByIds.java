package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovie;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesSimpleByIds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesSimpleByIds implements RetrieveMoviesSimpleByIds {

  private final RetrieveMovie retrieveMovie;

  public DefaultRetrieveMoviesSimpleByIds(RetrieveMovie retrieveMovie) {
    this.retrieveMovie = retrieveMovie;
  }

  @Override
  public List<MovieSimpleDto> apply(Model model) {
    validateModel(model);
    List<MovieSimpleDto> moviesDto = new ArrayList<>();
    model
        .getMoviesId()
        .forEach(
            movieId -> {
              Optional<MovieSimpleDto> movieDto =
                  retrieveMovie.apply(RetrieveMovie.Model.builder().movieId(movieId).build());
              movieDto.ifPresent(moviesDto::add);
            });

    return moviesDto;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getMoviesId())) {
      throw new BadRequestUseCaseException("Model moviesId is required");
    }
  }
}
