package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrievePopularMovies
    extends Function<RetrievePopularMovies.Model, Optional<List<MovieSimpleDTO>>> {
  @Getter
  @Builder
  class Model {
    private final Integer page;
    private final Integer size;
  }
}
