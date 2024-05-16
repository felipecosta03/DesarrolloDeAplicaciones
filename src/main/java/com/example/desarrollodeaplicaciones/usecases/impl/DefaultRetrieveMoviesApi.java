package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesApiRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesApiSort;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesApi;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesApi implements RetrieveMoviesApi {

  private final RetrieveMoviesApiRepository retrieveMoviesApiRepository;
  private final BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort;

  public DefaultRetrieveMoviesApi(
      RetrieveMoviesApiRepository retrieveMoviesApiRepository,
      BuildRetrieveMoviesApiSort buildRetrieveMoviesApiSort) {
    this.retrieveMoviesApiRepository = retrieveMoviesApiRepository;
    this.buildRetrieveMoviesApiSort = buildRetrieveMoviesApiSort;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    return retrieveMoviesApiRepository.apply(
        RetrieveMoviesApiRepository.Model.builder()
            .sort(
                buildRetrieveMoviesApiSort.apply(
                    BuildRetrieveMoviesApiSort.Model.builder()
                        .dateOrder(model.getDateOrder())
                        .qualificationOrder(model.getQualificationOrder())
                        .build()))
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
