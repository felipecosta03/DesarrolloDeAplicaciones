package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovies
    extends Function<RetrieveMovies.Model, Optional<List<MovieSimpleDto>>> {

  @Builder
  @Getter
  class Model {
    private final String dateOrder;
    private final String qualificationOrder;
    private final String genre;
    private final Integer page;
    private final Integer size;
  }
}
