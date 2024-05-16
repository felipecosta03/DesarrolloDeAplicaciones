package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface AddFavoriteMovie extends Consumer<AddFavoriteMovie.Model> {

  @Getter
  @Builder
  class Model {
    private Long userId;
    private Long movieId;
  }
}
