package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.usecases.BuildImageUrl;
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
  private final BuildImageUrl buildImageUrl;

  public DefaultSaveMoviesDto(
      SaveMovies saveMovies,
      BuildMovies buildMovies,
      FilterUnsavedMovies filterUnsavedMovies,
      BuildImageUrl buildImageUrl) {
    this.saveMovies = saveMovies;
    this.buildMovies = buildMovies;
    this.filterUnsavedMovies = filterUnsavedMovies;
    this.buildImageUrl = buildImageUrl;
  }

  @Override
  @Async
  public void accept(List<MovieSimpleDto> moviesDto) {
    moviesDto.forEach(
        movie -> {
          movie.setPosterPath(
              isNull(movie.getPosterPath()) ? null : buildImageUrl.apply(movie.getPosterPath()));
          movie.setBackdropPath(
              isNull(movie.getBackdropPath())
                  ? null
                  : buildImageUrl.apply(movie.getBackdropPath()));
        });

    List<MovieSimple> movies = buildMovies.apply(filterUnsavedMovies.apply(moviesDto));
    saveMovies.accept(movies);
  }
}
