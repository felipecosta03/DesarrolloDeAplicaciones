package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.ExistsUserByEmailRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultExistsUserByEmail implements ExistsUserByEmail {
  private final ExistsUserByEmailRepository existsUserByEmailRepository;

  public DefaultExistsUserByEmail(ExistsUserByEmailRepository existsUserByEmailRepository) {
    this.existsUserByEmailRepository = existsUserByEmailRepository;
  }

  @Override
  public Boolean apply(Model model) {
    validateModel(model);
    return existsUserByEmailRepository.existsByEmail(model.getEmail());
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
