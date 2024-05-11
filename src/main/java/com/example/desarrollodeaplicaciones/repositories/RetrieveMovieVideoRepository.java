package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideoApi;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieVideoRepository
    extends Function<RetrieveMovieVideoRepository.Model, Optional<ResponseMovieVideoApi>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
