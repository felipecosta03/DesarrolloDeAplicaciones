package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.UserUpdateDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import com.example.desarrollodeaplicaciones.usecases.UpdateUser;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

@Component
public class DefaultUpdateUser implements UpdateUser {

  private final SaveUser saveUser;
  private final RetrieveUserById retrieveUserById;

  public DefaultUpdateUser(SaveUser saveUser, RetrieveUserById retrieveUserById) {
    this.saveUser = saveUser;
    this.retrieveUserById = retrieveUserById;
  }

  @Override
  public void accept(Long userId, UserUpdateDto userDto) {
    validateUser(userDto);
    User user = retrieveUserById.apply(RetrieveUserById.Model.builder().userId(userId).build());
    user.setImage(userDto.getImage());
    user.setNickName(userDto.getNickName());
    saveUser.apply(user);
  }

  private void validateUser(UserUpdateDto userDto) {
    if (isNull(userDto)) {
      throw new BadRequestUseCaseException("User is required");
    }
    if (Strings.isNullOrEmpty(userDto.getNickName())) {
      throw new BadRequestUseCaseException("NickName is required");
    }
  }
}
