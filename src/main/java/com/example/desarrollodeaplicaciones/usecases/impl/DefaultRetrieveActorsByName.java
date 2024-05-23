package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByName;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByNameApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByNameDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveActorsDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class  DefaultRetrieveActorsByName implements RetrieveActorsByName {

  private final RetrieveActorsByNameApi retrieveActorsByNameApi;
  private final RetrieveActorsByNameDatabase retrieveActorsByNameDatabase;
  private final SaveActorsDto saveActorsDto;

  public DefaultRetrieveActorsByName(
      RetrieveActorsByNameApi retrieveActorsByNameApi,
      RetrieveActorsByNameDatabase retrieveActorsByNameDatabase,
      SaveActorsDto saveActorsDto) {
    this.retrieveActorsByNameApi = retrieveActorsByNameApi;
    this.retrieveActorsByNameDatabase = retrieveActorsByNameDatabase;
    this.saveActorsDto = saveActorsDto;
  }

  @Override
  public Optional<List<ActorDto>> apply(Model model) {

    Optional<List<ActorDto>> actors =
        retrieveActorsByNameApi.apply(
            RetrieveActorsByNameApi.Model.builder()
                .actorName(model.getActorName())
                .page(model.getPage())
                .size(model.getSize())
                .build());

    if (actors.isEmpty()) {
      return retrieveActorsByNameDatabase.apply(
          RetrieveActorsByNameDatabase.Model.builder()
              .actorName(model.getActorName())
              .page(model.getPage())
              .size(model.getSize())
              .build());
    }
    saveActorsDto.accept(actors.get());
    return actors;
  }
}
