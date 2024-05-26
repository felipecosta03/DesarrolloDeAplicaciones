package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RetrieveMoviesByTitleDatabaseRepository
    extends PagingAndSortingRepository<MovieSimple, Pageable> {
  List<MovieSimple> findAllByTitleContaining(String title, Pageable pageable);
}
