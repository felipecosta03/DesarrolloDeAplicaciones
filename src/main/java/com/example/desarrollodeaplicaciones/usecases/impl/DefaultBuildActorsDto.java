package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import com.example.desarrollodeaplicaciones.usecases.BuildActorDto;
import com.example.desarrollodeaplicaciones.usecases.BuildActorsDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildActorsDto implements BuildActorsDto {

  private final BuildActorDto buildActorDto;

  public DefaultBuildActorsDto(BuildActorDto buildActorDto) {
    this.buildActorDto = buildActorDto;
  }

  @Override
  public List<ActorDto> apply(List<Actor> actors) {
    return actors.stream().map(buildActorDto).toList();
  }
}
