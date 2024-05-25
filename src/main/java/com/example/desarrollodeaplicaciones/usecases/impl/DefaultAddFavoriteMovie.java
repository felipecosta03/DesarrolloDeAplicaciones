package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.MovieExistsByIdRepository;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.AddFavoriteMovie;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddFavoriteMovie implements AddFavoriteMovie {

  private final MovieExistsByIdRepository movieExistsByIdRepository;
  private final RetrieveUserById retrieveUserById;
  private final SaveUserRepository saveUserRepository;
  private final RetrieveMovieDetail retrieveMovieDetail;

  public DefaultAddFavoriteMovie(
      MovieExistsByIdRepository movieExistsByIdRepository,
      RetrieveUserById retrieveUserById,
      SaveUserRepository saveUserRepository,
      RetrieveMovieDetail retrieveMovieDetail) {
    this.movieExistsByIdRepository = movieExistsByIdRepository;
    this.retrieveUserById = retrieveUserById;
    this.saveUserRepository = saveUserRepository;
    this.retrieveMovieDetail = retrieveMovieDetail;
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    User user =
        retrieveUserById.apply(RetrieveUserById.Model.builder().userId(model.getUserId()).build());

    if (user.getFavoriteMovies().contains(model.getMovieId())) {
      throw new BadRequestUseCaseException("Movie already added to favorites");
    }

    if (!movieExistsByIdRepository.existsById(model.getMovieId())) {
      Optional<MovieDetailDto> movieDetail =
          retrieveMovieDetail.apply(
              RetrieveMovieDetail.Model.builder().movieId(model.getMovieId()).build());
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
