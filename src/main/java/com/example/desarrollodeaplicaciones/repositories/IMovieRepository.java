package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {}
