package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface RemoveDuplicatedMovies
    extends Function<List<MovieSimpleDto>, List<MovieSimpleDto>> {}
