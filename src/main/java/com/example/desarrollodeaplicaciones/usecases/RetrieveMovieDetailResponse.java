package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetailResponse
    extends Function<RetrieveMovieDetailResponse.Model, Optional<MovieDetailDto>> {

  @Getter
  @Builder
  class Model {
    private final Long movieId;
  }
}
