package com.example.desarrollodeaplicaciones.repositories.api;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieImageRepository
    extends Function<RetrieveMovieDetailApiRepository.Model, Optional<ResponseMovieImagesApi>> {
  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
