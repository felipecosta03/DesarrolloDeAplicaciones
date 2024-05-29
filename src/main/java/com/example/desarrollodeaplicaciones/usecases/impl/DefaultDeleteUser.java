package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.DeleteUserRepository;
import com.example.desarrollodeaplicaciones.repositories.ExistUserByIdRepository;
import com.example.desarrollodeaplicaciones.usecases.DeleteUser;
import org.springframework.stereotype.Component;

@Component
public class DefaultDeleteUser implements DeleteUser {

  private final DeleteUserRepository deleteUserRepository;
  private final ExistUserByIdRepository existUserByIdRepository;

  public DefaultDeleteUser(
      DeleteUserRepository deleteUserRepository, ExistUserByIdRepository existUserByIdRepository) {
    this.deleteUserRepository = deleteUserRepository;
    this.existUserByIdRepository = existUserByIdRepository;
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    if (!existUserByIdRepository.existsById(model.getUserId())) {
      throw new NotFoundUseCaseException("User not found");
    }
    deleteUserRepository.deleteById(model.getUserId());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getUserId())) {
      throw new BadRequestUseCaseException("User id is required");
    }
  }
}
