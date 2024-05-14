package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailDatabase;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailDatabase implements RetrieveMovieDetailDatabase {

  private final RetrieveMovieDetailDatabaseRepository retrieveMovieDetailDatabaseRepository;

  public DefaultRetrieveMovieDetailDatabase(
      RetrieveMovieDetailDatabaseRepository retrieveMovieDetailDatabaseRepository) {
    this.retrieveMovieDetailDatabaseRepository = retrieveMovieDetailDatabaseRepository;
  }

  @Override
  public Optional<MovieDetail> apply(Model model) {
    validateModel(model);
    return retrieveMovieDetailDatabaseRepository.findById(model.getMovieId());
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
