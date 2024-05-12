package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveActorsByNameDatabase
    extends Function<RetrieveActorsByNameDatabase.Model, Optional<List<ActorDto>>> {

  @Builder
  @Getter
  class Model {
    private String actorName;
    private Integer page;
    private Integer size;
    private String dateOrder;
    private String qualificationOrder;
  }
}
