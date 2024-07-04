package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieCreditsApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideoApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieImageRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviePeopleRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieVideoRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Optional;
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
  public Optional<MovieDetailDto> apply(Model model) {
    validateModel(model);
    Optional<MovieDetailDto> movieDetailDto =
        retrieveMovieDetailApiRepository.apply(
            RetrieveMovieDetailApiRepository.Model.builder().movieId(model.getMovieId()).build());

    if (movieDetailDto.isEmpty()) {
      return Optional.empty();
    }
    movieDetailDto.get().setVotes(new ArrayList<>());
    Optional<ResponseMovieImagesApi> responseMovieImagesApi =
        retrieveMovieImageRepository.apply(
            RetrieveMovieImageRepository.Model.builder().movieId(model.getMovieId()).build());

    Optional<ResponseMovieVideoApi> responseMovieVideoApi =
        retrieveMovieVideoRepository.apply(
            RetrieveMovieVideoRepository.Model.builder().movieId(model.getMovieId()).build());

    Optional<ResponseMovieCreditsApi> responseMovieCreditsApi =
        retrieveMoviePeopleRepository.apply(
            RetrieveMoviePeopleRepository.Model.builder().movieId(model.getMovieId()).build());

    responseMovieImagesApi.ifPresent(
        images -> movieDetailDto.get().setImages(images.getBackdrops()));
    responseMovieCreditsApi.ifPresent(
        credits -> {
          getDirector(credits).ifPresent(director -> movieDetailDto.get().setDirector(director));
          movieDetailDto
              .get()
              .setCast(
                  credits.getCast().stream()
                      .filter(
                          cast -> !isNull(cast) && !Strings.isNullOrEmpty(cast.getProfilePath()))
                      .toList());
        });
    responseMovieVideoApi.ifPresent(videos -> movieDetailDto.get().setVideos(videos.getResults()));
    return movieDetailDto;
  }

  private Optional<PeopleCrewDto> getDirector(ResponseMovieCreditsApi responseMovieCreditsApi) {
    return responseMovieCreditsApi.getCrew().stream()
        .filter(
            crew ->
                "Director".equals(crew.getJob()) && !Strings.isNullOrEmpty(crew.getProfilePath()))
        .findFirst();
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
