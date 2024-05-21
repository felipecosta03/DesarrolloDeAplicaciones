package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveUser implements SaveUser {
  private final SaveUserRepository saveUserRepository;

  public DefaultSaveUser(SaveUserRepository saveUserRepository) {
    this.saveUserRepository = saveUserRepository;
  }

  @Override
  public void accept(User user) {
    validateUser(user);
    saveUserRepository.save(user);
  }

  private void validateUser(User user) {
    if (isNull(user)) {
      throw new BadRequestUseCaseException("User cannot be null");
    }

    if (isNull(user.getEmail()) || user.getEmail().isEmpty()) {
      throw new BadRequestUseCaseException("User email is required");
    }
  }
}
