package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetailRepository apiMovieDetailRepository;
  private final RetrieveMovieDetailRepository defaultMovieDetailRepository;

  public DefaultRetrieveMovieDetailResponse(
      @Qualifier("themoviedb") RetrieveMovieDetailRepository apiMovieDetailRepository,
      @Qualifier("default") RetrieveMovieDetailRepository defaultMovieDetailRepository) {
    this.apiMovieDetailRepository = apiMovieDetailRepository;
    this.defaultMovieDetailRepository = defaultMovieDetailRepository;
  }

  @Override
  public MovieDetailDTO apply(Model model) {
    return apiMovieDetailRepository.apply(
        RetrieveMovieDetailRepository.Model.builder().movieId(model.getIdMovie()).build()).get();
  }
}
