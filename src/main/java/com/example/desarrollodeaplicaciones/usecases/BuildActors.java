package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import java.util.List;
import java.util.function.Function;

public interface BuildActors extends Function<List<ActorDto>, List<Actor>> {}
