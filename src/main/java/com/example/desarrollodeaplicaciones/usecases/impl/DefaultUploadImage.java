package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.InternalServerErrorUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.UploadImage;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultUploadImage implements UploadImage {

  private static final Logger log = LoggerFactory.getLogger(DefaultUploadImage.class);
  private final Cloudinary cloudinary;

  public DefaultUploadImage(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String apply(Model model) {
    validateImage(model);
    try {
      Map<?, ?> imageInfo =
          cloudinary.uploader().upload(model.getImageUrl(), ObjectUtils.emptyMap());
      log.info("Image uploaded successfully");
      return imageInfo.get("url").toString().replace("http", "https");
    } catch (Exception e) {
      throw new InternalServerErrorUseCaseException(e.getMessage());
    }
  }

  private void validateImage(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Image url is required");
    }
    if (isNull(model.getImageUrl())) {
      throw new BadRequestUseCaseException("Image url is required");
    }
  }
}
