package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface RetrieveMoviesDetailsByIdsDatabaseRepository extends Repository<MovieDetail, Long> {
  @Query("SELECT m FROM MovieDetail m WHERE m.id IN :moviesId")
  Optional<List<MovieDetail>> findAllById(List<Long> moviesId);
}
