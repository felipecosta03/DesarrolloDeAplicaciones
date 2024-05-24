package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.RetrieveUserByEmailRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserByEmail;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveUserByEmail implements RetrieveUserByEmail {
  private final RetrieveUserByEmailRepository retrieveUserByEmailRepository;

  public DefaultRetrieveUserByEmail(RetrieveUserByEmailRepository retrieveUserByEmailRepository) {
    this.retrieveUserByEmailRepository = retrieveUserByEmailRepository;
  }

  @Override
  public Optional<User> apply(Model model) {
    validateModel(model);
    return retrieveUserByEmailRepository.findUserByEmail(model.getEmail());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model cannot be null");
    }

    if (isNull(model.getEmail()) || model.getEmail().isEmpty()) {
      throw new BadRequestUseCaseException("Model email is required");
    }
  }
}
