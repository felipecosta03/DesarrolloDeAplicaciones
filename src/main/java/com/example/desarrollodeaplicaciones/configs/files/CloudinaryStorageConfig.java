package com.example.desarrollodeaplicaciones.configs.files;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryStorageConfig {
  private final String CLOUDINARY_URL;

  public CloudinaryStorageConfig(@Value("${CLOUDINARY_URL}") String cloudinaryUrl) {
    this.CLOUDINARY_URL = cloudinaryUrl;
  }

  @Bean
  public Cloudinary cloudinary() {
    Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
    cloudinary.config.secure = true;
    return cloudinary;
  }
}
