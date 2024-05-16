package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.usecases.BuildActors;
import com.example.desarrollodeaplicaciones.usecases.SaveActors;
import com.example.desarrollodeaplicaciones.usecases.SaveActorsDto;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveActorsDto implements SaveActorsDto {

  private final BuildActors buildActors;
  private final SaveActors saveActors;

  public DefaultSaveActorsDto(BuildActors buildActors, SaveActors saveActors) {
    this.buildActors = buildActors;
    this.saveActors = saveActors;
  }

  @Override
  @Async
  public void accept(List<ActorDto> actorDtos) {
    saveActors.accept(buildActors.apply(actorDtos));
  }
}
