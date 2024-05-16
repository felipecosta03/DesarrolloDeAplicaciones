package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.User;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveUserById extends Function<RetrieveUserById.Model, User> {

  @Builder
  @Getter
  class Model {
    private Long userId;
  }
}
