package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.List;
import java.util.function.Function;

public interface FilterUnsavedMovies extends Function<List<MovieSimpleDto>, List<MovieSimpleDto>> {}
