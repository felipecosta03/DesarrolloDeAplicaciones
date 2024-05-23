package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.User;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

@FunctionalInterface
public interface RetrieveUserByEmail extends Function<RetrieveUserByEmail.Model, Optional<User>> {

  @Getter
  @Builder
  class Model {
    private String email;
  }
}
