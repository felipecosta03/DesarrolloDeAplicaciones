package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrievePopularMoviesApiRepository
    extends Function<RetrievePopularMoviesApiRepository.Model, Optional<List<MovieSimpleDto>>> {
  @Builder
  @Getter
  class Model {
    private final Integer page;
    private final Integer size;
  }
}
