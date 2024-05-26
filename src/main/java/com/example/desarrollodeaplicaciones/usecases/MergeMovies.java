package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
public interface MergeMovies
    extends BiFunction<List<MovieSimpleDto>, List<MovieSimpleDto>, List<MovieSimpleDto>> {}
