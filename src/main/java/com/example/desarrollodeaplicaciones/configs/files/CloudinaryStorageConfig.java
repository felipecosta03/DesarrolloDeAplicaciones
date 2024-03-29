package com.example.desarrollodeaplicaciones.configs.files;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryStorageConfig {
  @Bean
  public Cloudinary cloudinary() {
    Dotenv dotenv = Dotenv.load();
    Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    cloudinary.config.secure = true;
    return cloudinary;
  }
}
