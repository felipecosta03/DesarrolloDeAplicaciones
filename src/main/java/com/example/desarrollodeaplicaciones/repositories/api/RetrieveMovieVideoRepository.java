package com.example.desarrollodeaplicaciones.repositories.api;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideoApi;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieVideoRepository
    extends Function<RetrieveMovieDetailApiRepository.Model, Optional<ResponseMovieVideoApi>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
