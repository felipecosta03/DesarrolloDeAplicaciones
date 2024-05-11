package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrievePopularMoviesDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDto;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrievePopularMoviesDatabase implements RetrievePopularMoviesDatabase {

  private final RetrievePopularMoviesDatabaseRepository retrievePopularMoviesDatabaseRepository;
  private final BuildMovieDto buildMovieDTO;

  public DefaultRetrievePopularMoviesDatabase(
      RetrievePopularMoviesDatabaseRepository retrievePopularMoviesDatabaseRepository,
      BuildMovieDto buildMovieDTO) {
    this.retrievePopularMoviesDatabaseRepository = retrievePopularMoviesDatabaseRepository;
    this.buildMovieDTO = buildMovieDTO;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    Pageable pageable = PageRequest.of(model.getPage(), model.getSize());

    return Optional.of(
        retrievePopularMoviesDatabaseRepository.findAll(pageable).getContent().stream()
            .map(buildMovieDTO)
            .toList());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
