package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.SaveMovieDetailRepository;
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
  private final SaveMovieDetailRepository saveMovieDetailRepository;
  private final BuildMovieDetail buildMovieDetail;
  private final BuildMovieDetailDto buildMovieDetailDto;
  private final FixImage<MovieDetailDto> fixImage;
  private final BuildVideoUrl buildVideoUrl;

  public DefaultRetrieveMovieDetail(
      RetrieveMovieDetailApi retrieveMovieDetailApi,
      RetrieveMovieDetailDatabase retrieveMovieDetailDatabase,
      SaveMovieDetail saveMovieDetail,
      SaveMovieDetailRepository saveMovieDetailRepository,
      BuildMovieDetail buildMovieDetail,
      BuildMovieDetailDto buildMovieDetailDto,
      FixImage<MovieDetailDto> fixImage,
      BuildVideoUrl buildVideoUrl) {
    this.retrieveMovieDetailApi = retrieveMovieDetailApi;
    this.retrieveMovieDetailDatabase = retrieveMovieDetailDatabase;
    this.saveMovieDetail = saveMovieDetail;
    this.saveMovieDetailRepository = saveMovieDetailRepository;
    this.buildMovieDetail = buildMovieDetail;
    this.buildMovieDetailDto = buildMovieDetailDto;
    this.fixImage = fixImage;
    this.buildVideoUrl = buildVideoUrl;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
    Optional<MovieDetail> movieDetail =
        retrieveMovieDetailDatabase.apply(
            RetrieveMovieDetailDatabase.Model.builder().movieId(model.getMovieId()).build());

    Optional<MovieDetailDto> movieDetailDto = movieDetail.map(buildMovieDetailDto);

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
          MovieDetail movieDetailBuilded = buildMovieDetail.apply(response);
          saveMovieDetailRepository.save(movieDetailBuilded);
          saveMovieDetail.accept(movieDetailBuilded);
        });

    return movieDetailDto;
  }
}
