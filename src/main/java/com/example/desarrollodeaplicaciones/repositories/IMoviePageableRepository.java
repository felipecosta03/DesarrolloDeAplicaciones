package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoviePageableRepository extends PagingAndSortingRepository<Movie, Long> {
  Page<Movie> findAllByGenre(Pageable pageable, String genre);

  @Query(
      "SELECT m FROM Movie m LEFT JOIN m.actors a WHERE m.title LIKE %:value% OR a.fullName LIKE %:value%")
  Page<Movie> findAllByTitleOrActor(Pageable pageable, String value);
}
