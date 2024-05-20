package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.function.Function;

public interface BuildMovieSimpleDtoByMovieDetailDto
    extends Function<MovieDetailDto, MovieSimpleDto> {}
