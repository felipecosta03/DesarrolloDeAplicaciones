package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.MovieExistsByIdRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveUserByIdRepository;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.AddFavoriteMoveToUser;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailResponse;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddFavoriteMoveToUser implements AddFavoriteMoveToUser {

  private final MovieExistsByIdRepository movieExistsByIdRepository;
  private final RetrieveUserByIdRepository retrieveUserByIdRepository;
  private final RetrieveMovieDetailResponse retrieveMovieDetailResponse;
  private final SaveUserRepository saveUserRepository;

  public DefaultAddFavoriteMoveToUser(
      MovieExistsByIdRepository movieExistsByIdRepository,
      RetrieveUserByIdRepository retrieveUserByIdRepository,
      RetrieveMovieDetailResponse retrieveMovieDetailResponse,
      SaveUserRepository saveUserRepository) {
    this.movieExistsByIdRepository = movieExistsByIdRepository;
    this.retrieveUserByIdRepository = retrieveUserByIdRepository;
    this.retrieveMovieDetailResponse = retrieveMovieDetailResponse;
    this.saveUserRepository = saveUserRepository;
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    User user =
        retrieveUserByIdRepository
            .findById(model.getUserId())
            .orElseThrow(() -> new NotFoundUseCaseException("User not found"));
    if (!movieExistsByIdRepository.existsById(model.getMovieId())) {
      Optional<MovieDetailDto> movieDetail =
          retrieveMovieDetailResponse.apply(
              RetrieveMovieDetailResponse.Model.builder().movieId(model.getMovieId()).build());
      if (movieDetail.isEmpty()) {
        throw new NotFoundUseCaseException("Movie not found");
      }
    }
    user.getFavoriteMovies().add(model.getMovieId());
    saveUserRepository.save(user);
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getUserId())) {
      throw new BadRequestUseCaseException("UserId is required");
    }
    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("MovieId is required");
    }
  }
}
