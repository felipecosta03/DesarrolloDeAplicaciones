package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.Comparator;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface BuildMoviesComparator
    extends Function<BuildMoviesComparator.Model, Comparator<MovieSimpleDto>> {
  @Builder
  @Getter
  class Model {
    private String dateOrder;
    private String qualificationOrder;
  }
}
