package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import org.springframework.data.repository.Repository;

public interface MovieExistsByIdRepository extends Repository<MovieSimple, Long> {
  boolean existsById(Long id);
}
