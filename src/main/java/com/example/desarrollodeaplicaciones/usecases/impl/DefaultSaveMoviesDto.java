package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveMoviesDto implements SaveMoviesDto {

  private SaveMovies saveMovies;
  private BuildMovies buildMovies;

  public DefaultSaveMoviesDto(SaveMovies saveMovies, BuildMovies buildMovies) {
    this.saveMovies = saveMovies;
    this.buildMovies = buildMovies;
  }

  @Override
  @Async
  public void accept(List<MovieSimpleDto> moviesDto) {
    List<MovieSimple> movies = buildMovies.apply(moviesDto);
    saveMovies.accept(movies);
  }
}
