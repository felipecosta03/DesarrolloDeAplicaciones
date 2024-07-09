package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RetrieveMoviesDetailsByIds
    extends Function<List<Long>, Optional<List<MovieDetail>>> {}
