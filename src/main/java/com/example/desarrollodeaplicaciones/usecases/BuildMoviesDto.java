package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.function.Function;

public interface BuildMoviesDto extends Function<List<MovieSimple>, List<MovieSimpleDto>> {}
