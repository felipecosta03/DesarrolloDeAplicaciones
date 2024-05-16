package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface DeleteFavoriteMovie extends Consumer<DeleteFavoriteMovie.Model> {

  @Getter
  @Builder
  class Model {
    private final Long userId;
    private final Long movieId;
  }
}
