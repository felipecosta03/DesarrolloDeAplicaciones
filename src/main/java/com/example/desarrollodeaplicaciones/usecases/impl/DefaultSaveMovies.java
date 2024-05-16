package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.repositories.SaveMoviesRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveMovies;
import com.example.desarrollodeaplicaciones.usecases.UploadImage;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultSaveMovies implements SaveMovies {

  private final SaveMoviesRepository saveMoviesRepository;
  private final UploadImage uploadImage;

  public DefaultSaveMovies(SaveMoviesRepository saveMoviesRepository, UploadImage uploadImage) {
    this.saveMoviesRepository = saveMoviesRepository;
    this.uploadImage = uploadImage;
  }

  @Override
  public void accept(List<MovieSimple> movies) {
    validateMovie(movies);
    movies.forEach(
        movie -> {
          try {
            movie.setPosterPath(
                uploadImage.apply(
                    UploadImage.Model.builder().imageUrl(movie.getPosterPath()).build()));
            movie.setBackdropPath(
                uploadImage.apply(
                    UploadImage.Model.builder().imageUrl(movie.getBackdropPath()).build()));
          } catch (Exception e) {
            log.error("Error uploading image: {}", e.getMessage());
          }
        });
    saveMoviesRepository.saveAll(movies);

    log.info("Movies saved successfully");
  }

  private void validateMovie(List<MovieSimple> movies) {
    if (isNull(movies)) {
      throw new BadRequestUseCaseException("MovieSimple is required");
    }

    if (movies.isEmpty()) {
      throw new BadRequestUseCaseException("MovieSimple list is empty");
    }

    if (movies.stream().anyMatch(Objects::isNull)) {
      throw new BadRequestUseCaseException("MovieSimple list contains null values");
    }

    if (movies.stream().anyMatch(movie -> isNull(movie.getId()))) {
      throw new BadRequestUseCaseException("MovieSimple list contains movies with null id");
    }
  }
}
