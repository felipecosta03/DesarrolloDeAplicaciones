package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveUserByIdRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildUserDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserResponse;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveUserResponse implements RetrieveUserResponse {

  private final RetrieveUserByIdRepository retrieveUserByIdRepository;
  private final BuildUserDto buildUserDto;

  public DefaultRetrieveUserResponse(
      RetrieveUserByIdRepository retrieveUserByIdRepository, BuildUserDto buildUserDto) {
    this.retrieveUserByIdRepository = retrieveUserByIdRepository;
    this.buildUserDto = buildUserDto;
  }

  @Override
  public Optional<UserDto> apply(Model model) {
    validateModel(model);
    return retrieveUserByIdRepository.findById(model.getUserId()).map(buildUserDto);
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
