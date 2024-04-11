package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieDetailPageableRepository extends PagingAndSortingRepository<MovieDetail, Long> {
  @Query("SELECT m FROM MovieDetail m LEFT JOIN m.genres g WHERE g.name = :genre")
  Page<MovieDetail> findAllByGenre(Pageable pageable, String genre);

  @Query(
      "SELECT m FROM MovieDetail m LEFT JOIN m.cast a WHERE m.title LIKE %:value% OR a.name LIKE %:value% or m.director.name LIKE %:value%")
  Page<MovieDetail> findAllByTitleOrActor(Pageable pageable, String value);
}
