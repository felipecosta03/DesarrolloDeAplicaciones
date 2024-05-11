package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesBySearch
    extends Function<RetrieveMoviesBySearch.Model, Optional<List<MovieSimpleDTO>>> {
  @Builder
  @Getter
  class Model {
    private final String value;
    private final Integer page;
    private final Integer size;
  }
}
