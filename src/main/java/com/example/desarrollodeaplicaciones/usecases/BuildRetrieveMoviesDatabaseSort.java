package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

public interface BuildRetrieveMoviesDatabaseSort
    extends Function<BuildRetrieveMoviesDatabaseSort.Model, Sort> {
  @Builder
  @Getter
  class Model {
    private String dateOrder;
    private String qualificationOrder;
  }
}
