package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetail retrieveMovieDetail;
  private final RetrieveUserById retrieveUserById;

  public DefaultRetrieveMovieDetailResponse(
      RetrieveMovieDetail retrieveMovieDetail, RetrieveUserById retrieveUserById) {
    this.retrieveMovieDetail = retrieveMovieDetail;
    this.retrieveUserById = retrieveUserById;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
    validateModel(model);

    Optional<MovieDetailDto> movieDetailDto =
        retrieveMovieDetail.apply(
            RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build());

    User user =
        retrieveUserById.apply(RetrieveUserById.Model.builder().userId(model.getUserId()).build());

    movieDetailDto.ifPresent(
        movie -> movie.setFavorite(user.getFavoriteMovies().contains(movie.getId())));

    return movieDetailDto;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("Movie id is required");
    }
    if (isNull(model.getUserId())) {
      throw new BadRequestUseCaseException("User id is required");
    }
  }
}
