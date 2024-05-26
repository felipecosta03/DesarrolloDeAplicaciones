package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByGenreDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesDatabaseSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByGenreDatabase implements RetrieveMoviesByGenreDatabase {

  private final RetrieveMoviesByGenreDatabaseRepository retrieveMoviesByGenreDatabaseRepository;
  private final BuildMoviesDto buildMoviesDto;
  private final BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort;

  public DefaultRetrieveMoviesByGenreDatabase(
      RetrieveMoviesByGenreDatabaseRepository retrieveMoviesByGenreDatabaseRepository,
      BuildMoviesDto buildMoviesDto,
      BuildRetrieveMoviesDatabaseSort buildRetrieveMoviesDatabaseSort) {
    this.retrieveMoviesByGenreDatabaseRepository = retrieveMoviesByGenreDatabaseRepository;
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
                    .qualificationOrder(model.getQualificationOrder())
                    .dateOrder(model.getDateOrder())
                    .build()));
    return retrieveMoviesByGenreDatabaseRepository
        .findByGenreIdsContaining(model.getGenreId(), pageable)
        .map(buildMoviesDto);
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getGenreId())) {
      throw new BadRequestUseCaseException("Genre id is required");
    }
  }
}
