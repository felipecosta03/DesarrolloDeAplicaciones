package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface BuildMovieDetail extends Function<BuildMovieDetail.Model, MovieDetail> {

  @Getter
  @Builder
  class Model {
    private MovieDetailDTO movieDetailDTO;
  }
}
