package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface DeleteUser extends Consumer<DeleteUser.Model> {
  @Builder
  @Getter
  class Model {
    private final Long userId;
  }
}
