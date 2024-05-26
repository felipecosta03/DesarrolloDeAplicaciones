package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface RetrieveMoviesSimpleByIdsDatabaseRepository extends Repository<MovieSimple, Long> {
  @Query("SELECT m FROM MovieSimple m WHERE m.id IN :moviesId")
  Optional<List<MovieSimple>> findAllById(List<Long> moviesId);
}
