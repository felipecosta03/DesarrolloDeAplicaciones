package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.exceptions.RetrieveMovieDetailResponseUseCaseException;
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
  public MovieDetailDTO apply(Model model) {
    validateModel(model);

    Optional<MovieDetail> movieDetail =
        retrieveMovieDetail.apply(
            RetrieveMovieDetail.Model.builder().movieId(model.getIdMovie()).build());

    if (movieDetail.isPresent()) {
      return buildMovieDetailDTO.apply(
          BuildMovieDetailDTO.Model.builder().movieDetail(movieDetail.get()).build());
    }

    MovieDetailDTO movieDetailApi =
        retrieveMovieDetailApi
            .apply(RetrieveMovieDetailApi.Model.builder().movieId(model.getIdMovie()).build());

    MovieDetail movieDetailToBeSaved =
        buildMovieDetail.apply(
            BuildMovieDetail.Model.builder().movieDetailDTO(movieDetailApi).build());

    saveMovieDetail.accept(
        SaveMovieDetail.Model.builder().movieDetail(movieDetailToBeSaved).build());

    return movieDetailApi;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new RetrieveMovieDetailResponseUseCaseException("Model is required");
    }
    if (isNull(model.getIdMovie())) {
      throw new RetrieveMovieDetailResponseUseCaseException("Movie id is required");
    }
  }
}
