package com.example.desarrollodeaplicaciones.usecases.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.desarrollodeaplicaciones.exceptions.usecases.InternalServerErrorUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.DeleteImage;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class DefaultDeleteImage implements DeleteImage {

  private final Cloudinary cloudinary;

  public DefaultDeleteImage(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public void accept(Model model) {
    try {
      cloudinary.uploader().destroy(model.getImageId(), ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new InternalServerErrorUseCaseException(e.getMessage());
    }
  }
}
