package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveActorsByNameDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildActorsDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByNameDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveActorsByNameDatabase implements RetrieveActorsByNameDatabase {

  private final RetrieveActorsByNameDatabaseRepository retrieveActorsByNamesDatabaseRepository;
  private final BuildActorsDto buildActorsDto;

  public DefaultRetrieveActorsByNameDatabase(
      RetrieveActorsByNameDatabaseRepository retrieveActorsByNamesDatabaseRepository,
      BuildActorsDto buildActorsDto) {
    this.retrieveActorsByNamesDatabaseRepository = retrieveActorsByNamesDatabaseRepository;
    this.buildActorsDto = buildActorsDto;
  }

  @Override
  public Optional<List<ActorDto>> apply(Model model) {
    validateModel(model);
    Pageable pageable = PageRequest.of(model.getPage(), model.getSize(), Sort.by(Sort.Order.asc("name")));
    return retrieveActorsByNamesDatabaseRepository
        .findAllByNameContaining(model.getActorName(), pageable)
        .map(buildActorsDto);
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
