package com.example.desarrollodeaplicaciones.entrypoints.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.entrypoints.RetrieveMovieDetailEndpoint;
import com.example.desarrollodeaplicaciones.models.ResponseDynamic;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailEndpoint implements RetrieveMovieDetailEndpoint {

  private final RetrieveMovieDetailResponse retrieveMovieDetailResponse;

  public DefaultRetrieveMovieDetailEndpoint(
      RetrieveMovieDetailResponse retrieveMovieDetailResponse) {
    this.retrieveMovieDetailResponse = retrieveMovieDetailResponse;
  }

  @Override
  public ResponseDynamic<MovieDetailDTO> apply(Model model) {
    return ResponseDynamic.<MovieDetailDTO>builder()
        .data(
            retrieveMovieDetailResponse.apply(
                RetrieveMovieDetailResponse.Model.builder().idMovie(model.getMovieId()).build()))
        .status(200)
        .build();
  }
}
