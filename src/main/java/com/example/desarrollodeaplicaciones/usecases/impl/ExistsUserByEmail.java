package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.repositories.ExistsUserByEmailRepository;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface ExistsUserByEmail extends Function<ExistsUserByEmail.Model, Boolean> {

  @Builder
  @Getter
  class Model {
    private String email;
  }
}
