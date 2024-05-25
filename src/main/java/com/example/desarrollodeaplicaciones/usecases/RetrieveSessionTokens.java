package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.Session;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveSessionTokens extends Function<RetrieveSessionTokens.Model, Session> {

  @Builder
  @Getter
  class Model {
    private String token;
  }
}
