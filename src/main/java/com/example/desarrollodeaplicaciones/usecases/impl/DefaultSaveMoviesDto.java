package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildMovies;
import com.example.desarrollodeaplicaciones.usecases.FilterUnsavedMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMovies;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveMoviesDto implements SaveMoviesDto {

  private final SaveMovies saveMovies;
  private final BuildMovies buildMovies;
  private final FilterUnsavedMovies filterUnsavedMovies;

  public DefaultSaveMoviesDto(
      SaveMovies saveMovies, BuildMovies buildMovies, FilterUnsavedMovies filterUnsavedMovies) {
    this.saveMovies = saveMovies;
    this.buildMovies = buildMovies;
    this.filterUnsavedMovies = filterUnsavedMovies;
  }

  @Override
  @Async
  public void accept(List<MovieSimpleDto> moviesDto) {
    moviesDto.forEach(
        movie -> {
          movie.setPosterPath(
              isNull(movie.getPosterPath())
                  ? null
                  : String.format("https://image.tmdb.org/t/p/w500%s", movie.getPosterPath()));
          movie.setBackdropPath(
              isNull(movie.getBackdropPath())
                  ? null
                  : String.format("https://image.tmdb.org/t/p/w500%s", movie.getBackdropPath()));
        });

    List<MovieSimple> movies = buildMovies.apply(filterUnsavedMovies.apply(moviesDto));
    saveMovies.accept(movies);
  }
}
