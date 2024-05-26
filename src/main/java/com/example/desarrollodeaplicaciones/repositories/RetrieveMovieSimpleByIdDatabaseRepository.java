package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RetrieveMovieSimpleByIdDatabaseRepository extends Repository<MovieSimple, Long> {
  Optional<MovieSimple> findById(Long id);
}
