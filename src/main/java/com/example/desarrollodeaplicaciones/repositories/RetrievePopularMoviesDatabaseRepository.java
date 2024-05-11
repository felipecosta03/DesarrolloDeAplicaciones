package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RetrievePopularMoviesDatabaseRepository
    extends PagingAndSortingRepository<MovieSimple, Pageable> {


}
