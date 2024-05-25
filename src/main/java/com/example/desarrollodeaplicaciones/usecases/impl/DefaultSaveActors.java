package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import com.example.desarrollodeaplicaciones.repositories.SaveActorsRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveActors;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveActors implements SaveActors {

  private final SaveActorsRepository saveActorsRepository;

  public DefaultSaveActors(SaveActorsRepository saveActorsRepository) {
    this.saveActorsRepository = saveActorsRepository;
  }

  @Override
  public void accept(List<Actor> actors) {
    validateActors(actors);
    saveActorsRepository.saveAll(actors);
  }

  private void validateActors(List<Actor> actors) {
    if (actors.isEmpty()) {
      throw new BadRequestUseCaseException("Actors list is empty");
    }
    if (actors.stream().anyMatch(actor -> actor.getName().isEmpty())) {
      throw new BadRequestUseCaseException("Actor name is required");
    }
    if (actors.stream().anyMatch(actor -> isNull(actor.getId()))) {
      throw new BadRequestUseCaseException("Actor id is required");
    }
  }
}
