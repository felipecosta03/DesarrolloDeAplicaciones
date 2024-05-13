package com.example.desarrollodeaplicaciones.configs.files;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CloudinaryStorage implements IFilesStorage {
  private final Cloudinary cloudinary;

  public CloudinaryStorage(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String uploadFile(MultipartFile file) {
    try {
      Map imageInfo = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
      return imageInfo.get("url").toString();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void deleteFile(String id) {
    try {
      cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public String uploadImage(String imageUrl) {
    try {
      Map<?, ?> imageInfo = cloudinary.uploader().upload(imageUrl, ObjectUtils.emptyMap());
      return imageInfo.get("url").toString();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException();
    }
  }
}
