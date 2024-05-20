package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailDatabase;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetailApi retrieveMovieDetailApi;
  private final RetrieveMovieDetailDatabase retrieveMovieDetailDatabase;
  private final SaveMovieDetail saveMovieDetail;
  private final BuildMovieDetail buildMovieDetail;
  private final BuildMovieDetailDto buildMovieDetailDTO;

  public DefaultRetrieveMovieDetailResponse(
      RetrieveMovieDetailDatabase retrieveMovieDetailDatabase,
      SaveMovieDetail saveMovieDetail,
      BuildMovieDetail buildMovieDetail,
      BuildMovieDetailDto buildMovieDetailDTO,
      RetrieveMovieDetailApi retrieveMovieDetailApi) {
    this.retrieveMovieDetailDatabase = retrieveMovieDetailDatabase;
    this.saveMovieDetail = saveMovieDetail;
    this.buildMovieDetail = buildMovieDetail;
    this.buildMovieDetailDTO = buildMovieDetailDTO;
    this.retrieveMovieDetailApi = retrieveMovieDetailApi;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
    validateModel(model);

    Optional<MovieDetail> movieDetail =
        retrieveMovieDetailDatabase.apply(
            RetrieveMovieDetailDatabase.Model.builder().movieId(model.getMovieId()).build());

    Optional<MovieDetailDto> movieDetailDto = movieDetail.map(buildMovieDetailDTO);

    if (movieDetailDto.isPresent()) {
      return movieDetailDto;
    }

    movieDetailDto =
        retrieveMovieDetailApi.apply(
            RetrieveMovieDetailApi.Model.builder().movieId(model.getMovieId()).build());

    movieDetailDto.ifPresent(
        response -> {
          saveMovieDetail.accept(buildMovieDetail.apply(response));
        });

    return movieDetailDto;
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
