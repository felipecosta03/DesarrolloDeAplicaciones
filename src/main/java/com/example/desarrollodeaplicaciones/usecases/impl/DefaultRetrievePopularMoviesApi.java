package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrievePopularMoviesApiRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrievePopularMoviesApi implements RetrievePopularMoviesApi {

  private final RetrievePopularMoviesApiRepository retrievePopularMoviesApiRepository;

  public DefaultRetrievePopularMoviesApi(
      RetrievePopularMoviesApiRepository retrievePopularMoviesApiRepository) {
    this.retrievePopularMoviesApiRepository = retrievePopularMoviesApiRepository;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    return retrievePopularMoviesApiRepository.apply(
        RetrievePopularMoviesApiRepository.Model.builder()
            .page(model.getPage())
            .size(model.getSize())
            .build());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
