package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMovieDetailApi
    extends Function<RetrieveMovieDetailApi.Model, MovieDetailDTO> {

  @Getter
  @Builder
  class Model {
    private final Long movieId;
  }
}
