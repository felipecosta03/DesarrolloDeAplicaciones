package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.SaveUserRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import com.example.desarrollodeaplicaciones.usecases.UploadImage;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveUser implements SaveUser {
  private final SaveUserRepository saveUserRepository;
  private final UploadImage uploadImage;

  public DefaultSaveUser(SaveUserRepository saveUserRepository, UploadImage uploadImage) {
    this.saveUserRepository = saveUserRepository;
    this.uploadImage = uploadImage;
  }

  @Override
  public User apply(User user) {
    validateUser(user);
    if (isNull(user.getImage())) {
      user.setImage(
          uploadImage.apply(UploadImage.Model.builder().imageUrl(user.getImage()).build()));
    }
    return saveUserRepository.save(user);
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
