package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieCreditsApi;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviePeopleRepository
    extends Function<RetrieveMoviePeopleRepository.Model, Optional<ResponseMovieCreditsApi>> {

  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
