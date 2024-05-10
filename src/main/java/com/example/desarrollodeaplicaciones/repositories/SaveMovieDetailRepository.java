package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import org.springframework.data.repository.Repository;

public interface SaveMovieDetailRepository extends Repository<MovieDetail, Long> {
  void save(MovieDetail movieDetail);
}
