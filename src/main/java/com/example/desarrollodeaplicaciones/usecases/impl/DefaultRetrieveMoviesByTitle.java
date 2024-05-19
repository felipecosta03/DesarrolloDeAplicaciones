package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitle;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitleApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitleDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByTitle implements RetrieveMoviesByTitle {

  private final RetrieveMoviesByTitleApi retrieveMoviesByTitleApi;
  private final RetrieveMoviesByTitleDatabase retrieveMoviesByTitleDatabase;
  private final SaveMoviesDto saveMoviesDto;

  public DefaultRetrieveMoviesByTitle(
      RetrieveMoviesByTitleApi retrieveMoviesByTitleApi,
      RetrieveMoviesByTitleDatabase retrieveMoviesByTitleDatabase,
      SaveMoviesDto saveMoviesDto) {
    this.retrieveMoviesByTitleApi = retrieveMoviesByTitleApi;
    this.retrieveMoviesByTitleDatabase = retrieveMoviesByTitleDatabase;
    this.saveMoviesDto = saveMoviesDto;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);

    // Retrieve movies by title from the API
    final Optional<List<MovieSimpleDto>> movies =
        retrieveMoviesByTitleApi.apply(
            RetrieveMoviesByTitleApi.Model.builder().title(model.getTitle()).build());

    // Retrieve movies by title from the database if an API error occurs (Optional is empty)
    if (movies.isEmpty()) {
      return retrieveMoviesByTitleDatabase.apply(
          RetrieveMoviesByTitleDatabase.Model.builder()
              .title(model.getTitle())
              .page(model.getPage())
              .size(model.getSize())
              .dateOrder(model.getDateOrder())
              .qualificationOrder(model.getQualificationOrder())
              .build());
    }
    // Save movies in the database
    saveMoviesDto.accept(movies.get());
    return movies;
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
