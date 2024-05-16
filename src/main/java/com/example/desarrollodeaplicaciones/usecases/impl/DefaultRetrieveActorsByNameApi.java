package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveActorsByNameApiRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByNameApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveActorsByNameApi implements RetrieveActorsByNameApi {

  private final RetrieveActorsByNameApiRepository retrieveActorsByNameApiRepository;

  public DefaultRetrieveActorsByNameApi(
      RetrieveActorsByNameApiRepository retrieveActorsByNameApiRepository) {
    this.retrieveActorsByNameApiRepository = retrieveActorsByNameApiRepository;
  }

  @Override
  public Optional<List<ActorDto>> apply(Model model) {
    validateModel(model);
    return retrieveActorsByNameApiRepository.apply(
        RetrieveActorsByNameApiRepository.Model.builder()
            .actorName(model.getActorName())
            .page(model.getPage())
            .size(model.getSize())
            .build());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getActorName().isEmpty()) {
      throw new BadRequestUseCaseException("Actor name is required");
    }
  }
}
