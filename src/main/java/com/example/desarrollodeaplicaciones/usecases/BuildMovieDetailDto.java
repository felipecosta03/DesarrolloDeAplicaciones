package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.function.Function;

public interface BuildMovieDetailDto extends Function<MovieDetail, MovieDetailDto> {}
