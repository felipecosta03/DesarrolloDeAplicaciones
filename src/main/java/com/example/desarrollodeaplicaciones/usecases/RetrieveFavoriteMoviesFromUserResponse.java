package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveFavoriteMoviesFromUserResponse
    extends Function<RetrieveFavoriteMoviesFromUserResponse.Model, Optional<List<MovieSimpleDto>>> {

  @Builder
  @Getter
  class Model {
    private final Long userId;
  }
}
