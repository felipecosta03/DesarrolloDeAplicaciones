package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovies implements RetrieveMovies {
  private final RetrieveMoviesDatabase retrieveMoviesDatabase;
  private final RetrieveMoviesApi retrieveMoviesApi;
  private final SaveMoviesDto saveMoviesDto;
  private final FixImage<MovieSimpleDto> fixImage;

  public DefaultRetrieveMovies(
      RetrieveMoviesDatabase retrieveMoviesDatabase,
      RetrieveMoviesApi retrieveMoviesApi,
      SaveMoviesDto saveMoviesDto,
      DefaultFixMovieImage fixImage) {
    this.retrieveMoviesDatabase = retrieveMoviesDatabase;
    this.retrieveMoviesApi = retrieveMoviesApi;
    this.saveMoviesDto = saveMoviesDto;
    this.fixImage = fixImage;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    Optional<List<MovieSimpleDto>> moviesDto =
        retrieveMoviesApi.apply(
            RetrieveMoviesApi.Model.builder()
                .page(model.getPage())
                .size(model.getSize())
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());

    if (moviesDto.isEmpty()) {
      return retrieveMoviesDatabase.apply(
          RetrieveMoviesDatabase.Model.builder()
              .page(model.getPage())
              .size(model.getSize())
              .dateOrder(model.getDateOrder())
              .qualificationOrder(model.getQualificationOrder())
              .build());
    }
    moviesDto.get().forEach(fixImage);
    saveMoviesDto.accept(moviesDto.get());
    return moviesDto;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
