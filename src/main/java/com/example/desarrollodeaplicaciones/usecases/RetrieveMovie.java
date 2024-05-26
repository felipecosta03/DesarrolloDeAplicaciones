package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovie extends Function<RetrieveMovie.Model, Optional<MovieSimpleDto>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
