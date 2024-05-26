package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserByEmail;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMovieDetailResponse implements RetrieveMovieDetailResponse {

  private final RetrieveMovieDetail retrieveMovieDetail;
  private final RetrieveUserByEmail retrieveUserByEmail;

  public DefaultRetrieveMovieDetailResponse(
      RetrieveMovieDetail retrieveMovieDetail, RetrieveUserByEmail retrieveUserByEmail) {
    this.retrieveMovieDetail = retrieveMovieDetail;
    this.retrieveUserByEmail = retrieveUserByEmail;
  }

  @Override
  public Optional<MovieDetailDto> apply(Model model) {
    validateModel(model);

    Optional<MovieDetailDto> movieDetailDto =
        retrieveMovieDetail.apply(
            RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build());
    Optional<User> user =
        retrieveUserByEmail.apply(
            RetrieveUserByEmail.Model.builder().email(model.getUserEmail()).build());

    if (user.isEmpty()) {
      log.info(model.getUserEmail());
      throw new BadRequestUseCaseException("User not found");
    }

    movieDetailDto.ifPresent(
        movie -> movie.setFavorite(user.get().getFavoriteMovies().contains(movie.getId())));

    return movieDetailDto;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("Movie id is required");
    }
    if (isNull(model.getUserEmail())) {
      throw new BadRequestUseCaseException("User email is required");
    }
  }
}
