package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface RetrieveGenresApiRepository extends Supplier<Optional<List<GenreDTO>>> {}
