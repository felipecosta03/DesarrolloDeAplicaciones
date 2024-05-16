package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.DeleteFavoriteMovie;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import org.springframework.stereotype.Component;

@Component
public class DefaultDeleteFavoriteMovie implements DeleteFavoriteMovie {
  private final RetrieveUserById retrieveUserById;
  private final SaveUserRepository saveUserRepository;

  public DefaultDeleteFavoriteMovie(
      RetrieveUserById retrieveUserById, SaveUserRepository saveUserRepository) {
    this.retrieveUserById = retrieveUserById;
    this.saveUserRepository = saveUserRepository;
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    User user =
        retrieveUserById.apply(RetrieveUserById.Model.builder().userId(model.getUserId()).build());
    if (!user.getFavoriteMovies().remove(model.getMovieId())) {
      throw new NotFoundUseCaseException("Movie not found");
    }
    saveUserRepository.save(user);
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
