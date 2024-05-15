package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesSimpleById
    extends Function<RetrieveMoviesSimpleById.Model, List<MovieSimpleDto>> {
  @Getter
  @Builder
  class Model {
    private List<Long> moviesId;
  }
}
