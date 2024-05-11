package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetailApiRepository
    extends Function<RetrieveMovieDetailApiRepository.Model, Optional<MovieDetailDTO>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
