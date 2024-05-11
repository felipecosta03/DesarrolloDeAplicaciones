package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesResponse
    extends Function<RetrieveMoviesResponse.Model, Optional<List<MovieSimpleDto>>> {

  @Builder
  @Getter
  class Model {
    private final Optional<String> dateOrder;
    private final Optional<String> genre;
    private final Optional<String> qualificationOrder;
    private final Optional<Boolean> popularMovies;
    private final Optional<String> value;
    private final Optional<Integer> page;
    private final Optional<Integer> size;
  }
}
