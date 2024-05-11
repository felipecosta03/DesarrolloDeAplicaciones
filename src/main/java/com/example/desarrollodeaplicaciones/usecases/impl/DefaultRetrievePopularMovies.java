package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.usecases.RetrievePopularMovies;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrievePopularMovies implements RetrievePopularMovies {
  @Override
  public Optional<List<MovieSimpleDTO>> apply(Model model) {
    return Optional.empty();
  }
}
