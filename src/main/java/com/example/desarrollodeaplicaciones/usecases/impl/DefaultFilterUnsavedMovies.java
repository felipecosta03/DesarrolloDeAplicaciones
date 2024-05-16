package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.repositories.MovieExistsByIdRepository;
import com.example.desarrollodeaplicaciones.usecases.FilterUnsavedMovies;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultFilterUnsavedMovies implements FilterUnsavedMovies {

  private final MovieExistsByIdRepository movieExistsByIdRepository;

  public DefaultFilterUnsavedMovies(MovieExistsByIdRepository movieExistsByIdRepository) {
    this.movieExistsByIdRepository = movieExistsByIdRepository;
  }

  @Override
  public List<MovieSimpleDto> apply(List<MovieSimpleDto> moviesSimpleDto) {
    return moviesSimpleDto.stream()
        .filter(movie -> !movieExistsByIdRepository.existsById(movie.getId()))
        .toList();
  }
}
