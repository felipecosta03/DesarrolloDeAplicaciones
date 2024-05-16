package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.function.Function;

public interface BuildMovie extends Function<MovieSimpleDto, MovieSimple> {}
