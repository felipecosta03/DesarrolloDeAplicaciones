package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import com.example.desarrollodeaplicaciones.usecases.UpdateUser;
import org.springframework.stereotype.Component;

@Component
public class DefaultUpdateUser implements UpdateUser {

  private final SaveUserRepository saveUserRepository;
  private final RetrieveUserById retrieveUserById;

  public DefaultUpdateUser(
      SaveUserRepository saveUserRepository, RetrieveUserById retrieveUserById) {
    this.saveUserRepository = saveUserRepository;
    this.retrieveUserById = retrieveUserById;
  }

  @Override
  public void accept(UserDto userDto) {
    validateUser(userDto);
    User user =
        retrieveUserById.apply(RetrieveUserById.Model.builder().userId(userDto.getId()).build());
    user.setActive(userDto.isActive());
    user.setImage(userDto.getImage());
    user.setNickName(userDto.getNickName());
    saveUserRepository.save(user);
  }

  private void validateUser(UserDto userDto) {
    if (isNull(userDto)) {
      throw new BadRequestUseCaseException("User is required");
    }

    if (isNull(userDto.getId())) {
      throw new BadRequestUseCaseException("User id is required");
    }
  }
}
