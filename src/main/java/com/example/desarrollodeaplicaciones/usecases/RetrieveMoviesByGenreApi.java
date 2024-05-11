package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesByGenreApi
    extends Function<RetrieveMoviesByGenreApi.Model, Optional<List<MovieSimpleDTO>>> {
  @Builder
  @Getter
  class Model {
    private final String dateOrder;
    private final String qualificationOrder;
    private final Integer genreId;
    private final Integer page;
    private final Integer size;
  }
}
