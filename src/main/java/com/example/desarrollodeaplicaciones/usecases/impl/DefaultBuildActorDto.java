package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import com.example.desarrollodeaplicaciones.usecases.BuildActorDto;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildActorDto implements BuildActorDto {

  private final BuildMoviesDto buildMoviesDto;

  public DefaultBuildActorDto(BuildMoviesDto buildMoviesDto) {
    this.buildMoviesDto = buildMoviesDto;
  }

  @Override
  public ActorDto apply(Actor actor) {
    validateActor(actor);
    return ActorDto.builder()
        .name(actor.getName())
        .id(actor.getId())
        .knownFor(buildMoviesDto.apply(actor.getKnownFor()))
        .build();
  }

  private void validateActor(Actor actor) {
    if (isNull(actor)) {
      throw new BadRequestUseCaseException("Actor is required");
    }

    if (actor.getName().isEmpty()) {
      throw new BadRequestUseCaseException("Actor name is required");
    }
  }
}
