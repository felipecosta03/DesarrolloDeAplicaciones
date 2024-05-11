package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.function.Function;

public interface BuildMovieDTO extends Function<MovieSimple, MovieSimpleDTO> {}
