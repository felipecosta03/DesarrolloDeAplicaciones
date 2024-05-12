package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import java.util.List;
import java.util.function.Consumer;

public interface SaveActors extends Consumer<List<Actor>> {}
