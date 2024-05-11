package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDTO;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveMoviesDTO implements SaveMoviesDTO {

  private SaveMovies saveMovies;
  private BuildMovies buildMovies;

  public DefaultSaveMoviesDTO(SaveMovies saveMovies, BuildMovies buildMovies) {
    this.saveMovies = saveMovies;
    this.buildMovies = buildMovies;
  }

  @Override
  @Async
  public void accept(List<MovieSimpleDTO> moviesDTO) {
    List<MovieSimple> movies = buildMovies.apply(moviesDTO);
    saveMovies.accept(movies);
  }
}
