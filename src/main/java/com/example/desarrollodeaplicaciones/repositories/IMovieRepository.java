package com.example.desarrollodeaplicaciones.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.desarrollodeaplicaciones.models.Movie;

public interface IMovieRepository extends MongoRepository<Movie, String> {}
