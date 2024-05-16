package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesApiRepository
    extends Function<RetrieveMoviesApiRepository.Model, Optional<List<MovieSimpleDto>>> {
  @Builder
  @Getter
  class Model {
    private final String sort;
    private final Integer page;
    private final Integer size;
  }
}
