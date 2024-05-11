package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface BuildRetrieveMoviesApiSort
    extends Function<BuildRetrieveMoviesApiSort.Model, String> {
  @Builder
  @Getter
  class Model {
    private String dateOrder;
    private String qualificationOrder;
  }
}
