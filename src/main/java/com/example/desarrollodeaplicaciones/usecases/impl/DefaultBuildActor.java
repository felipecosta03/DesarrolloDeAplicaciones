package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import com.example.desarrollodeaplicaciones.usecases.BuildActor;
import com.example.desarrollodeaplicaciones.usecases.BuildMovies;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildActor implements BuildActor {

  private final BuildMovies buildMovies;

  public DefaultBuildActor(BuildMovies buildMovies) {
    this.buildMovies = buildMovies;
  }

  @Override
  public Actor apply(ActorDto actor) {
    validateActor(actor);
    return Actor.builder()
        .name(actor.getName())
        .id(actor.getId())
        .knownFor(buildMovies.apply(actor.getKnownFor()))
        .build();
  }

  private void validateActor(ActorDto actor) {
    if (isNull(actor)) {
      throw new BadRequestUseCaseException("Actor is required");
    }

    if (actor.getName().isEmpty()) {
      throw new BadRequestUseCaseException("Actor name is required");
    }
  }
}
