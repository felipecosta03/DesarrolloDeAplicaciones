package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetail
    extends Function<RetrieveMovieDetail.Model, Optional<MovieDetailDto>> {
  @Builder
  @Getter
  class Model {
    private final Long movieId;
  }
}
