package com.example.desarrollodeaplicaciones.entrypoints;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.models.ResponseDynamic;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetailEndpoint
    extends Function<RetrieveMovieDetailEndpoint.Model, ResponseDynamic<MovieDetailDTO>> {

  @Builder
  @Getter
  class Model {
    private Long movieId;
  }
}
