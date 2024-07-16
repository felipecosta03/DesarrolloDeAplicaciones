package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.repositories.RetrieveGenresApiRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveGenres;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveGenres implements RetrieveGenres {

  private final RetrieveGenresApiRepository retrieveGenresApiRepository;

  public DefaultRetrieveGenres(RetrieveGenresApiRepository retrieveGenresApiRepository) {
    this.retrieveGenresApiRepository = retrieveGenresApiRepository;
  }

  @Override
  public List<GenreDto> get() {
    return retrieveGenresApiRepository.get().orElse(new ArrayList<>());
  }
}
