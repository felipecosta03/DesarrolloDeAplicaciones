package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveFavoriteMoviesFromUserResponse;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesSimpleByIds;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveFavoriteMoviesFromUserResponse
    implements RetrieveFavoriteMoviesFromUserResponse {
  private final BuildMoviesDto buildMoviesDto;
  private final RetrieveUserById retrieveUserById;
  private final RetrieveMoviesSimpleByIds retrieveMoviesSimpleByIds;

  public DefaultRetrieveFavoriteMoviesFromUserResponse(
      BuildMoviesDto buildMoviesDto,
      RetrieveUserById retrieveUserById,
      RetrieveMoviesSimpleByIds retrieveMoviesSimpleByIds) {
    this.buildMoviesDto = buildMoviesDto;
    this.retrieveUserById = retrieveUserById;
    this.retrieveMoviesSimpleByIds = retrieveMoviesSimpleByIds;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    User user =
        retrieveUserById.apply(RetrieveUserById.Model.builder().userId(model.getUserId()).build());

    return Optional.of(
        retrieveMoviesSimpleByIds.apply(
            RetrieveMoviesSimpleByIds.Model.builder().moviesId(user.getFavoriteMovies()).build()));
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new IllegalArgumentException("Model is required");
    }

    if (isNull(model.getUserId())) {
      throw new IllegalArgumentException("Model userId is required");
    }
  }
}
