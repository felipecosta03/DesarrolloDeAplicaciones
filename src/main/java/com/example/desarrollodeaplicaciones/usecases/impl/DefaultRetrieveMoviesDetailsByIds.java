package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesDetailsByIdsDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesDetailsByIds;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesDetailsByIds implements RetrieveMoviesDetailsByIds {
  private RetrieveMoviesDetailsByIdsDatabaseRepository retrieveMoviesDetailsByIdsDatabaseRepository;

  public DefaultRetrieveMoviesDetailsByIds(
      RetrieveMoviesDetailsByIdsDatabaseRepository retrieveMoviesDetailsByIdsDatabaseRepository) {
    this.retrieveMoviesDetailsByIdsDatabaseRepository =
        retrieveMoviesDetailsByIdsDatabaseRepository;
  }

  @Override
  public Optional<List<MovieDetail>> apply(List<Long> ids) {
    return retrieveMoviesDetailsByIdsDatabaseRepository.findAllById(ids);
  }
}
