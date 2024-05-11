package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface SaveMoviesRepository extends Repository<MovieSimple, Long> {
  void save(List<MovieSimple> movies);
}
