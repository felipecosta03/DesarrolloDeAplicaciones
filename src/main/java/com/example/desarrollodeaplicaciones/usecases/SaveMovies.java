package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.function.Consumer;

public interface SaveMovies extends Consumer<List<MovieSimple>> {}
