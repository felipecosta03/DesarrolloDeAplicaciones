package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDto;
import com.example.desarrollodeaplicaciones.usecases.BuildVideoUrl;
import com.example.desarrollodeaplicaciones.usecases.FixImage;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetail implements RetrieveMovieDetail {
  private final RetrieveMovieDetailApi retrieveMovieDetailApi;
  private final RetrieveMovieDetailDatabase retrieveMovieDetailDatabase;
  private final SaveMovieDetail saveMovieDetail;
  private final BuildMovieDetail buildMovieDetail;
  private final BuildMovieDetailDto buildMovieDetailDTO;
  private final FixImage<MovieDetailDto> fixImage;
  private final BuildVideoUrl buildVideoUrl;

  public DefaultRetrieveMovieDetail(
      RetrieveMovieDetailApi retrieveMovieDetailApi,
      RetrieveMovieDetailDatabase retrieveMovieDetailDatabase,
      SaveMovieDetail saveMovieDetail,
      BuildMovieDetail buildMovieDetail,
      BuildMovieDetailDto buildMovieDetailDTO,
      FixImage<MovieDetailDto> fixImage,
      BuildVideoUrl buildVideoUrl) {
    this.retrieveMovieDetailApi = retrieveMovieDetailApi;
    this.retrieveMovieDetailDatabase = retrieveMovieDetailDatabase;
    this.saveMovieDetail = saveMovieDetail;
    this.buildMovieDetail = buildMovieDetail;
    this.buildMovieDetailDTO = buildMovieDetailDTO;
    this.fixImage = fixImage;
    this.buildVideoUrl = buildVideoUrl;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
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
          fixImage.accept(response);
          response.getVideos().forEach(video -> video.setKey(buildVideoUrl.apply(video.getKey())));
          saveMovieDetail.accept(buildMovieDetail.apply(response));
        });

    return movieDetailDto;
  }
}
