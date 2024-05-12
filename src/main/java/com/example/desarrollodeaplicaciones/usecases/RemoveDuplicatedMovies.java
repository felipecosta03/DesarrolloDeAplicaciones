package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
/**
 * Merge two lists of movies.
 *
 * @param List<MovieSimpleDto> the first list of movies.
 * @param List<MovieSimpleDto> the second list of movies.
 * @return List<MovieSimpleDto> the merged list of movies.
 */
public interface RemoveDuplicatedMovies
    extends Function<List<MovieSimpleDto>, List<MovieSimpleDto>> {}
