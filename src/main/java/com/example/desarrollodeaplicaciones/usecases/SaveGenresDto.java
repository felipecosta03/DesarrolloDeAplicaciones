package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import java.util.List;
import java.util.function.Consumer;

public interface SaveGenresDto extends Consumer<List<GenreDto>> {}
