package com.example.desarrollodeaplicaciones.configs;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryStorageConfig {
  private final String cloudinaryUrl;

  public CloudinaryStorageConfig(@Value("${CLOUDINARY_URL}") String cloudinaryUrl) {
    this.cloudinaryUrl = cloudinaryUrl;
  }

  @Bean
  public Cloudinary cloudinary() {
    Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
    cloudinary.config.secure = true;
    return cloudinary;
  }
}
