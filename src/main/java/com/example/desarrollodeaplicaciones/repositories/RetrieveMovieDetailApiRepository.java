package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetailApiRepository
    extends Function<RetrieveMovieDetailApiRepository.Model, Optional<MovieDetailDto>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
