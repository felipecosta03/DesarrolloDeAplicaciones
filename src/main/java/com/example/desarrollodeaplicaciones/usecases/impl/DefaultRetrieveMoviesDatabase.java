package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDto;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesDatabaseSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesDatabase implements RetrieveMoviesDatabase {

  private final RetrieveMoviesDatabaseRepository retrieveMoviesDatabaseRepository;
  private final BuildMovieDto buildMovieDTO;
  private final BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort;

  public DefaultRetrieveMoviesDatabase(
      RetrieveMoviesDatabaseRepository retrieveMoviesDatabaseRepository,
      BuildMovieDto buildMovieDTO,
      BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort) {
    this.retrieveMoviesDatabaseRepository = retrieveMoviesDatabaseRepository;
    this.buildMovieDTO = buildMovieDTO;
    this.buildRetrieveMoviesDatabaseSort = buildRetrieveMoviesDatabaseSort;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    Pageable pageable =
        PageRequest.of(
            model.getPage(),
            model.getSize(),
            buildRetrieveMoviesDatabaseSort.apply(
                BuildRetrieveMoviesDatabaseSort.Model.builder()
                    .qualificationOrder(model.getQualificationOrder())
                    .dateOrder(model.getDateOrder())
                    .build()));
    return Optional.of(
        retrieveMoviesDatabaseRepository.findAll(pageable).getContent().stream()
            .map(buildMovieDTO)
            .toList());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
