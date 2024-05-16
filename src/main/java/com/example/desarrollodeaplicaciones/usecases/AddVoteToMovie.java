package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface AddVoteToMovie extends Consumer<AddVoteToMovie.Model> {
  @Builder
  @Getter
  class Model {
    private final Long userId;
    private final Long movieId;
    private final Integer score;
  }
}
