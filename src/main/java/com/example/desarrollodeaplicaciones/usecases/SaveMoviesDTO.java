package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import java.util.List;
import java.util.function.Consumer;

public interface SaveMoviesDTO extends Consumer<List<MovieSimpleDTO>> {}
