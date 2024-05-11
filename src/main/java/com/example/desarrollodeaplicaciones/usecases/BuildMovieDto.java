package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.function.Function;

public interface BuildMovieDto extends Function<MovieSimple, MovieSimpleDto> {}
