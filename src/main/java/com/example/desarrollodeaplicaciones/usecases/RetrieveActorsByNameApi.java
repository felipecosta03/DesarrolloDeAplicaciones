package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveActorsByNameApi
    extends Function<RetrieveActorsByNameApi.Model, Optional<List<ActorDto>>> {

  @Builder
  @Getter
  class Model {
    private String actorName;
    private Integer page;
    private Integer size;
  }
}
