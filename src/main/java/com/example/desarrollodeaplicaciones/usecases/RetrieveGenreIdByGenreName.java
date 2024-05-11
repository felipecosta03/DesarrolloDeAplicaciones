package com.example.desarrollodeaplicaciones.usecases;

import java.util.Optional;
import java.util.function.Function;

public interface RetrieveGenreIdByGenreName extends Function<String, Optional<Integer>> {}
