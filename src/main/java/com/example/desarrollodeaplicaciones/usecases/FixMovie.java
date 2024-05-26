package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import java.util.function.Consumer;

@FunctionalInterface
public interface FixMovie extends Consumer<MovieSimpleDto> {}
