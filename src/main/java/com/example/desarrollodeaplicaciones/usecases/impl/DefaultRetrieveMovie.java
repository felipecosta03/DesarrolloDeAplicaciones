package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieSimpleByIdDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDto;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieSimpleDtoByMovieDetailDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovie;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovie implements RetrieveMovie {
  private final RetrieveMovieDetail retrieveMovieDetail;
  private final BuildMovieSimpleDtoByMovieDetailDto buildMovieSimpleDtoByMovieDetailDto;
  private final RetrieveMovieSimpleByIdDatabaseRepository retrieveMovieSimpleByIdDatabaseRepository;
  private final BuildMovieDto buildMovieDto;

  public DefaultRetrieveMovie(
      RetrieveMovieDetail retrieveMovieDetail,
      BuildMovieSimpleDtoByMovieDetailDto buildMovieSimpleDtoByMovieDetailDto,
      RetrieveMovieSimpleByIdDatabaseRepository retrieveMovieSimpleByIdDatabaseRepository,
      BuildMovieDto buildMovieDto) {
    this.retrieveMovieDetail = retrieveMovieDetail;
    this.buildMovieSimpleDtoByMovieDetailDto = buildMovieSimpleDtoByMovieDetailDto;
    this.retrieveMovieSimpleByIdDatabaseRepository = retrieveMovieSimpleByIdDatabaseRepository;
    this.buildMovieDto = buildMovieDto;
  }

  @Override
  public Optional<MovieSimpleDto> apply(Model model) {
    validateModel(model);
    Optional<MovieSimple> movie =
        retrieveMovieSimpleByIdDatabaseRepository.findById(model.getMovieId());
    if (movie.isPresent()) {
      return movie.map(buildMovieDto);
    }
    return retrieveMovieDetail
        .apply(RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build())
        .map(buildMovieSimpleDtoByMovieDetailDto);
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
