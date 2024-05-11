package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import java.util.List;
import java.util.function.Consumer;

public interface SaveGenresDTO extends Consumer<List<GenreDTO>> {}
