package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.People;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {
  @Query("SELECT m FROM Movie m LEFT JOIN m.actors a WHERE a=:people OR m.director=:people")
  List<Movie> findByPeople(People people);
}
