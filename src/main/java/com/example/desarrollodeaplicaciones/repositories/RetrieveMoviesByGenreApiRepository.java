package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesByGenreApiRepository
    extends Function<RetrieveMoviesByGenreApiRepository.Model, Optional<List<MovieSimpleDTO>>> {
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
