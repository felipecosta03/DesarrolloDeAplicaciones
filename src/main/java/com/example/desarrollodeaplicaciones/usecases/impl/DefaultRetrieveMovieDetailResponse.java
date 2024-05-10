package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDTO;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetail retrieveMovieDetail;
  private final SaveMovieDetail saveMovieDetail;
  private final BuildMovieDetail buildMovieDetail;
  private final BuildMovieDetailDTO buildMovieDetailDTO;
  private final RetrieveMovieDetailApi retrieveMovieDetailApi;

  public DefaultRetrieveMovieDetailResponse(
      RetrieveMovieDetail retrieveMovieDetail,
      SaveMovieDetail saveMovieDetail,
      BuildMovieDetail buildMovieDetail,
      BuildMovieDetailDTO buildMovieDetailDTO,
      RetrieveMovieDetailApi retrieveMovieDetailApi) {
    this.retrieveMovieDetail = retrieveMovieDetail;
    this.saveMovieDetail = saveMovieDetail;
    this.buildMovieDetail = buildMovieDetail;
    this.buildMovieDetailDTO = buildMovieDetailDTO;
    this.retrieveMovieDetailApi = retrieveMovieDetailApi;
  }

  @Override
  public Optional<MovieDetailDTO> apply(Model model) {
    validateModel(model);

    Optional<MovieDetail> movieDetail =
        retrieveMovieDetail.apply(
            RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build());

    Optional<MovieDetailDTO> movieDetailDTO =
        movieDetail
            .map(buildMovieDetailDTO)
            .or(
                () ->
                    retrieveMovieDetailApi.apply(
                        RetrieveMovieDetailApi.Model.builder()
                            .movieId(model.getMovieId())
                            .build()));

    if (movieDetailDTO.isEmpty()) {
      return Optional.empty();
    }

    MovieDetail movieDetailToBeSaved = buildMovieDetail.apply(movieDetailDTO.get());
    saveMovieDetail.accept(
        SaveMovieDetail.Model.builder().movieDetail(movieDetailToBeSaved).build());

    return movieDetailDTO;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("Movie id is required");
    }
  }
}
