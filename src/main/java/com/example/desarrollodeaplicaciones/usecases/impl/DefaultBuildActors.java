package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import com.example.desarrollodeaplicaciones.usecases.BuildActor;
import com.example.desarrollodeaplicaciones.usecases.BuildActors;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildActors implements BuildActors {

  private final BuildActor buildActor;

  public DefaultBuildActors(BuildActor buildActor) {
    this.buildActor = buildActor;
  }

  @Override
  public List<Actor> apply(List<ActorDto> actors) {
    return actors.stream().map(buildActor).toList();
  }
}
