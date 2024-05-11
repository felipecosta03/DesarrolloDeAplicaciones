package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetailApi retrieveMovieDetailApi;
  private final RetrieveMovieDetail retrieveMovieDetail;
  private final SaveMovieDetail saveMovieDetail;
  private final BuildMovieDetail buildMovieDetail;
  private final BuildMovieDetailDto buildMovieDetailDTO;

  public DefaultRetrieveMovieDetailResponse(
      RetrieveMovieDetail retrieveMovieDetail,
      SaveMovieDetail saveMovieDetail,
      BuildMovieDetail buildMovieDetail,
      BuildMovieDetailDto buildMovieDetailDTO,
      RetrieveMovieDetailApi retrieveMovieDetailApi) {
    this.retrieveMovieDetail = retrieveMovieDetail;
    this.saveMovieDetail = saveMovieDetail;
    this.buildMovieDetail = buildMovieDetail;
    this.buildMovieDetailDTO = buildMovieDetailDTO;
    this.retrieveMovieDetailApi = retrieveMovieDetailApi;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
    validateModel(model);

    Optional<MovieDetail> movieDetail =
        retrieveMovieDetail.apply(
            RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build());

    Optional<MovieDetailDto> movieDetailDTO = movieDetail.map(buildMovieDetailDTO);

    if (movieDetailDTO.isPresent()) {
      return movieDetailDTO;
    }
    movieDetailDTO =
        retrieveMovieDetailApi.apply(
            RetrieveMovieDetailApi.Model.builder().movieId(model.getMovieId()).build());

    if (movieDetailDTO.isEmpty()) {
      return Optional.empty();
    }

    MovieDetail movieDetailToBeSaved = buildMovieDetail.apply(movieDetailDTO.get());
    saveMovieDetail.accept(movieDetailToBeSaved);

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
