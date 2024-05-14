package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RetrieveMovieDetailDatabaseRepository extends Repository<MovieDetail, Long> {

  Optional<MovieDetail> findById(Long id);
}
