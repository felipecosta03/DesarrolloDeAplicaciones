package com.example.desarrollodeaplicaciones.repositories;


import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieDetailRepository extends JpaRepository<MovieDetail, Long> {}
