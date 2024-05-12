package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RemoveDuplicatedMovies;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultRemoveDuplicatedMovies implements RemoveDuplicatedMovies {
  @Override
  public List<MovieSimpleDto> apply(List<MovieSimpleDto> movies) {
    final List<MovieSimpleDto> moviesAux = new ArrayList<>();
    movies.forEach(
        movie -> {
          if (!moviesAux.contains(movie)) {
            moviesAux.add(movie);
          }
        });

    return moviesAux;
  }
}
