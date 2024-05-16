package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
/**
 * Merge two lists of movies.
 *
 * @param List<MovieSimpleDto> the first list of movies.
 * @param List<MovieSimpleDto> the second list of movies.
 * @return List<MovieSimpleDto> the merged list of movies.
 */
public interface MergeMovies
    extends BiFunction<
        List<MovieSimpleDto>, List<MovieSimpleDto>, Optional<List<MovieSimpleDto>>> {}
