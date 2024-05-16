package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByGenreApiRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesApiSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByGenreApi implements RetrieveMoviesByGenreApi {

  private final BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort;
  private final RetrieveMoviesByGenreApiRepository retrieveMoviesByGenreApiRepository;

  public DefaultRetrieveMoviesByGenreApi(
      BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort,
      RetrieveMoviesByGenreApiRepository retrieveMoviesByGenreApiRepository) {
    this.buildRetrieveMoviesApiSort = buildRetrieveMoviesApiSort;
    this.retrieveMoviesByGenreApiRepository = retrieveMoviesByGenreApiRepository;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    return retrieveMoviesByGenreApiRepository.apply(
        RetrieveMoviesByGenreApiRepository.Model.builder()
            .sort(
                buildRetrieveMoviesApiSort.apply(
                    BuildRetrieveMoviesApiSort.Model.builder()
                        .qualificationOrder(model.getQualificationOrder())
                        .dateOrder(model.getDateOrder())
                        .build()))
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
