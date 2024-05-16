package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.RetrieveUserByIdRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveUserById implements RetrieveUserById {
  private final RetrieveUserByIdRepository retrieveUserByIdRepository;

  public DefaultRetrieveUserById(RetrieveUserByIdRepository retrieveUserByIdRepository) {
    this.retrieveUserByIdRepository = retrieveUserByIdRepository;
  }

  @Override
  public User apply(Model model) {
    return retrieveUserByIdRepository
        .findById(model.getUserId())
        .orElseThrow(() -> new NotFoundUseCaseException("User not found"));
  }
}
