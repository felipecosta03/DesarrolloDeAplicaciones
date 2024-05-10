package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetail implements RetrieveMovieDetail {

  private final RetrieveMovieDetailRepository retrieveMovieDetailRepository;

  public DefaultRetrieveMovieDetail(RetrieveMovieDetailRepository retrieveMovieDetailRepository) {
    this.retrieveMovieDetailRepository = retrieveMovieDetailRepository;
  }

  @Override
  public Optional<MovieDetail> apply(Model model) {
    validateModel(model);
    return retrieveMovieDetailRepository.findById(model.getMovieId());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }

    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("Movie id is required");
    }
  }
}
