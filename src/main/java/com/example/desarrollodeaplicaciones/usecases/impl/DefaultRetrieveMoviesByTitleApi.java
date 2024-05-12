package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByTitleApiRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesApiSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitleApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByTitleApi implements RetrieveMoviesByTitleApi {

  private final RetrieveMoviesByTitleApiRepository retrieveMoviesByTitleApiRepository;
  private final BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort;

  public DefaultRetrieveMoviesByTitleApi(
      RetrieveMoviesByTitleApiRepository retrieveMoviesByTitleApiRepository,
      BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort) {
    this.retrieveMoviesByTitleApiRepository = retrieveMoviesByTitleApiRepository;
    this.buildRetrieveMoviesApiSort = buildRetrieveMoviesApiSort;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    return retrieveMoviesByTitleApiRepository.apply(
        RetrieveMoviesByTitleApiRepository.Model.builder()
            .sort(
                buildRetrieveMoviesApiSort.apply(
                    BuildRetrieveMoviesApiSort.Model.builder()
                        .qualificationOrder(model.getQualificationOrder())
                        .dateOrder(model.getDateOrder())
                        .build()))
            .title(model.getTitle())
            .page(model.getPage())
            .size(model.getSize())
            .build());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getTitle().isEmpty()) {
      throw new BadRequestUseCaseException("Title is required");
    }
  }
}
