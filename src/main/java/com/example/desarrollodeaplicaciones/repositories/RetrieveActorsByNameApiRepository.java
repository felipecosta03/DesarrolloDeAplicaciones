package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveActorsByNameApiRepository
    extends Function<RetrieveActorsByNameApiRepository.Model, Optional<List<ActorDto>>> {
  @Builder
  @Getter
  class Model {
    private final String actorName;
    private final Integer page;
    private final Integer size;
  }
}
