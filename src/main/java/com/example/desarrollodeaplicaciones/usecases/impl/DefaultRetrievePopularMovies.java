package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesApi;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultRetrievePopularMovies implements RetrievePopularMovies {

  private final RetrievePopularMoviesApi retrievePopularMoviesApi;
  private final RetrievePopularMoviesDatabase retrievePopularMoviesDatabase;
  private final SaveMoviesDto saveMoviesDto;
  private final FixImage<MovieSimpleDto> fixImage;

  public DefaultRetrievePopularMovies(
      RetrievePopularMoviesApi retrievePopularMoviesApi,
      RetrievePopularMoviesDatabase retrievePopularMoviesDatabase,
      SaveMoviesDto saveMoviesDto,
      FixImage<MovieSimpleDto> fixImage) {
    this.retrievePopularMoviesApi = retrievePopularMoviesApi;
    this.retrievePopularMoviesDatabase = retrievePopularMoviesDatabase;
    this.saveMoviesDto = saveMoviesDto;
    this.fixImage = fixImage;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    Optional<List<MovieSimpleDto>> moviesDto =
        retrievePopularMoviesApi.apply(
            RetrievePopularMoviesApi.Model.builder()
                .size(model.getSize())
                .page(model.getPage())
                .build());

    if (moviesDto.isPresent()) {
      moviesDto.get().forEach(fixImage);
      saveMoviesDto.accept(moviesDto.get());
      return moviesDto;
    }
    log.info("Retrieve popular movies from api failed, trying from database");
    return retrievePopularMoviesDatabase.apply(
        RetrievePopularMoviesDatabase.Model.builder()
            .size(model.getSize())
            .page(model.getPage())
            .build());
  }
}
