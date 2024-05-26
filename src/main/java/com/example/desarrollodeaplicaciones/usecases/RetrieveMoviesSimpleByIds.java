package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesSimpleByIds
    extends Function<RetrieveMoviesSimpleByIds.Model, List<MovieSimpleDto>> {
  @Getter
  @Builder
  class Model {
    private List<Long> moviesId;
  }
}
