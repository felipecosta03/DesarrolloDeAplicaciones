package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import java.util.List;
import java.util.function.Supplier;

public interface RetrieveGenres extends Supplier<List<GenreDto>> {}
