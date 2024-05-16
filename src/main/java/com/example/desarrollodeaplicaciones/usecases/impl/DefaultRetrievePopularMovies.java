package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesApi;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMoviesDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrievePopularMovies implements RetrievePopularMovies {

  private final RetrievePopularMoviesApi retrievePopularMoviesApi;
  private final RetrievePopularMoviesDatabase retrievePopularMoviesDatabase;
  private final SaveMoviesDto saveMoviesDTO;

  public DefaultRetrievePopularMovies(
      RetrievePopularMoviesApi retrievePopularMoviesApi,
      RetrievePopularMoviesDatabase retrievePopularMoviesDatabase,
      SaveMoviesDto saveMoviesDTO) {
    this.retrievePopularMoviesApi = retrievePopularMoviesApi;
    this.retrievePopularMoviesDatabase = retrievePopularMoviesDatabase;
    this.saveMoviesDTO = saveMoviesDTO;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
      Optional<List<MovieSimpleDto>> moviesDTO =
          retrievePopularMoviesApi.apply(
              RetrievePopularMoviesApi.Model.builder()
                  .size(model.getSize())
                  .page(model.getPage())
                  .build());

      if (moviesDTO.isPresent()) {
        saveMoviesDTO.accept(moviesDTO.get());
        return moviesDTO;
      }
    return retrievePopularMoviesDatabase.apply(
        RetrievePopularMoviesDatabase.Model.builder()
            .size(model.getSize())
            .page(model.getPage())
            .build());
  }
}
