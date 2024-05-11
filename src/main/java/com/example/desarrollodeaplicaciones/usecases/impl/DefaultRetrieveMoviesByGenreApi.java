package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByGenreApiRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByGenreApi implements RetrieveMoviesByGenreApi {

  private final RetrieveMoviesByGenreApiRepository retrieveMoviesByGenreApiRepository;

  public DefaultRetrieveMoviesByGenreApi(
      RetrieveMoviesByGenreApiRepository retrieveMoviesByGenreApiRepository) {
    this.retrieveMoviesByGenreApiRepository = retrieveMoviesByGenreApiRepository;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    return retrieveMoviesByGenreApiRepository.apply(
        RetrieveMoviesByGenreApiRepository.Model.builder()
            .dateOrder(model.getDateOrder())
            .qualificationOrder(model.getQualificationOrder())
            .genreId(model.getGenreId())
            .page(model.getPage())
            .size(model.getSize())
            .build());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }

  }
}
