package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDTO;
import com.example.desarrollodeaplicaciones.exceptions.RetrieveMovieDetailResponseUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.RetrieveMovieDetailUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieCreditsApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideoApi;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieImageRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMoviePeopleRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieVideoRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailApi implements RetrieveMovieDetailApi {

  private final RetrieveMovieDetailApiRepository retrieveMovieDetailApiRepository;
  private final RetrieveMoviePeopleRepository retrieveMoviePeopleRepository;
  private final RetrieveMovieVideoRepository retrieveMovieVideoRepository;
  private final RetrieveMovieImageRepository retrieveMovieImageRepository;

  public DefaultRetrieveMovieDetailApi(
      RetrieveMovieDetailApiRepository retrieveMovieDetailApiRepository,
      RetrieveMoviePeopleRepository retrieveMoviePeopleRepository,
      RetrieveMovieVideoRepository retrieveMovieVideoRepository,
      RetrieveMovieImageRepository retrieveMovieImageRepository) {
    this.retrieveMovieDetailApiRepository = retrieveMovieDetailApiRepository;
    this.retrieveMoviePeopleRepository = retrieveMoviePeopleRepository;
    this.retrieveMovieVideoRepository = retrieveMovieVideoRepository;
    this.retrieveMovieImageRepository = retrieveMovieImageRepository;
  }

  @Override
  public MovieDetailDTO apply(Model model) {
    validateModel(model);
    MovieDetailDTO movieDetailDTO =
        retrieveMovieDetailApiRepository
            .apply(
                RetrieveMovieDetailApiRepository.Model.builder()
                    .movieId(model.getMovieId())
                    .build())
            .orElseThrow(() -> new RetrieveMovieDetailResponseUseCaseException("Movie not found"));

    ResponseMovieImagesApi responseMovieImagesApi =
        retrieveMovieImageRepository
            .apply(
                RetrieveMovieDetailApiRepository.Model.builder()
                    .movieId(model.getMovieId())
                    .build())
            .orElseThrow(() -> new RetrieveMovieDetailResponseUseCaseException("Images not found"));

    movieDetailDTO.setImages(responseMovieImagesApi.getBackdrops());

    ResponseMovieVideoApi responseMovieVideoApi =
        retrieveMovieVideoRepository
            .apply(
                RetrieveMovieDetailApiRepository.Model.builder()
                    .movieId(model.getMovieId())
                    .build())
            .orElseThrow(() -> new RetrieveMovieDetailResponseUseCaseException("Video not found"));

    movieDetailDTO.setVideos(responseMovieVideoApi.getResults());

    ResponseMovieCreditsApi responseMovieCreditsApi =
        retrieveMoviePeopleRepository
            .apply(
                RetrieveMovieDetailApiRepository.Model.builder()
                    .movieId(model.getMovieId())
                    .build())
            .orElseThrow(
                () -> new RetrieveMovieDetailResponseUseCaseException("Credits not found"));

    movieDetailDTO.setDirector(getDirector(responseMovieCreditsApi));
    movieDetailDTO.setCast(responseMovieCreditsApi.getCast());

    return movieDetailDTO;
  }

  private PeopleCrewDTO getDirector(ResponseMovieCreditsApi responseMovieCreditsApi) {
    return responseMovieCreditsApi.getCrew().stream()
        .filter(crew -> "Director".equals(crew.getJob()))
        .findFirst()
        .orElseThrow(() -> new RetrieveMovieDetailResponseUseCaseException("Director not found"));
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new RetrieveMovieDetailUseCaseException("Model is required");
    }

    if (isNull(model.getMovieId())) {
      throw new RetrieveMovieDetailUseCaseException("Movie id is required");
    }
  }
}
