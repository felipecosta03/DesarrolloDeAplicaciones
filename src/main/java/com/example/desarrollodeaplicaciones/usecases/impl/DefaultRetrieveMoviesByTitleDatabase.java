package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByTitleDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesDatabaseSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitleDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByTitleDatabase implements RetrieveMoviesByTitleDatabase {

  private final RetrieveMoviesByTitleDatabaseRepository retrieveMoviesByTitleDatabaseRepository;
  private final BuildMoviesDto buildMoviesDto;
  private final BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort;

  public DefaultRetrieveMoviesByTitleDatabase(
      RetrieveMoviesByTitleDatabaseRepository retrieveMoviesByTitleDatabaseRepository,
      BuildMoviesDto buildMoviesDto,
      BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort) {
    this.retrieveMoviesByTitleDatabaseRepository = retrieveMoviesByTitleDatabaseRepository;
    this.buildMoviesDto = buildMoviesDto;
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
                    .dateOrder(model.getDateOrder())
                    .qualificationOrder(model.getQualificationOrder())
                    .build()));
    return retrieveMoviesByTitleDatabaseRepository
        .findAllByTitleContaining(model.getTitle(), pageable)
        .map(buildMoviesDto);
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getTitle().isEmpty()) {
      throw new BadRequestUseCaseException("Title is required");
    }
  }
}
