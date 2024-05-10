package com.example.desarrollodeaplicaciones.repositories.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("default")
public class DefaultRetrieveMovieDetailRepository implements RetrieveMovieDetailRepository {
  @Override
  public Optional<MovieDetailDTO> apply(Model model) {
    return Optional.empty();
  }
}
