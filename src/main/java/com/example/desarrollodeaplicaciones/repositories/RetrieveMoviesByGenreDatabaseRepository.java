package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RetrieveMoviesByGenreDatabaseRepository
    extends PagingAndSortingRepository<MovieSimple, Pageable> {

  Optional<List<MovieSimple>> findByGenreIdsContaining(Integer genreId, Pageable pageable);
}
