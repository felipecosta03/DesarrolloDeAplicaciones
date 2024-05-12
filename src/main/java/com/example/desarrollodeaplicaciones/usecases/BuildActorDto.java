package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import java.util.function.Function;

public interface BuildActorDto extends Function<Actor, ActorDto> {}
