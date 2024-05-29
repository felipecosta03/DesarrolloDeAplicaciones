package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.BuildUserDto;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildUserDto implements BuildUserDto {
  @Override
  public UserDto apply(User user) {
    validateUser(user);
    return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .lastName(user.getLastName())
        .nickName(user.getNickName())
        .image(user.getImage())
        .build();
  }

  private void validateUser(User user) {
    if (isNull(user)) {
      throw new BadRequestUseCaseException("User is required");
    }

    if (isNull(user.getId())) {
      throw new BadRequestUseCaseException("User id is required");
    }
  }
}
