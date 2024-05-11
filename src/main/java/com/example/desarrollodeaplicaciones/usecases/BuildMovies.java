package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.function.Function;

public interface BuildMovies extends Function<List<MovieSimpleDTO>, List<MovieSimple>> {}
