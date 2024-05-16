package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import org.springframework.data.repository.Repository;

public interface SaveMoviesRepository extends Repository<MovieSimple, Long> {
  <S extends MovieSimple> void saveAll(Iterable<S> entities);
}
