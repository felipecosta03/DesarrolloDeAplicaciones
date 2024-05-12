package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveMoviesByTitleDatabase
    extends Function<RetrieveMoviesByTitleDatabase.Model, Optional<List<MovieSimpleDto>>> {
  @Builder
  @Getter
  class Model {
    private String title;
    private Integer page;
    private Integer size;
    private String dateOrder;
    private String qualificationOrder;
  }
}
