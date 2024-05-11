package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovies;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovies implements RetrieveMovies {
  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    return Optional.empty();
  }
}
