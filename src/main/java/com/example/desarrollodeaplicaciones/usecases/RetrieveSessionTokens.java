package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Function;

import com.example.desarrollodeaplicaciones.models.Session;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveSessionTokens extends Function<RetrieveSessionTokens.Model, Session> {

  @Builder
  @Getter
  class Model {
    private String token;
  }
}
