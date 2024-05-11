package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.repositories.SaveMoviesRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveMovies;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultSaveMovies implements SaveMovies {

  private final SaveMoviesRepository saveMoviesRepository;

  public DefaultSaveMovies(SaveMoviesRepository saveMoviesRepository) {
    this.saveMoviesRepository = saveMoviesRepository;
  }

  @Override
  public void accept(List<MovieSimple> movies) {
    validateMovie(movies);
    saveMoviesRepository.saveAll(movies);
    log.info("Movies saved successfully");
  }

  private void validateMovie(List<MovieSimple> movies) {
    if (isNull(movies)) {
      throw new BadRequestRepositoryException("MovieSimple is required");
    }

    if (movies.isEmpty()) {
      throw new BadRequestRepositoryException("MovieSimple list is empty");
    }

    if (movies.stream().anyMatch(Objects::isNull)) {
      throw new BadRequestRepositoryException("MovieSimple list contains null values");
    }

    if (movies.stream().anyMatch(movie -> isNull(movie.getId()))) {
      throw new BadRequestRepositoryException("MovieSimple list contains movies with null id");
    }
  }
}
