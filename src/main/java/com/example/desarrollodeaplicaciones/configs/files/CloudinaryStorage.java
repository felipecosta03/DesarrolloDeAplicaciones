package com.example.desarrollodeaplicaciones.configs.files;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.desarrollodeaplicaciones.exceptions.ImageDeleteException;
import com.example.desarrollodeaplicaciones.exceptions.ImageUploadException;
import com.example.desarrollodeaplicaciones.models.Media;
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
  public Media uploadFile(MultipartFile file) {
    try {
      Map imageInfo = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
      return Media.builder()
          .url(imageInfo.get("url").toString())
          .id(imageInfo.get("public_id").toString())
          .build();
    } catch (IOException e) {
      throw new ImageUploadException();
    }
  }

  @Override
  public void deleteFile(String id) {
    try {
      cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    } catch (IOException e) {
      throw new ImageDeleteException();
    }
  }

  public String uploadImage(String imageUrl) {
    try {
      Map imageInfo = cloudinary.uploader().upload(imageUrl, ObjectUtils.emptyMap());
      return imageInfo.get("url").toString();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new ImageUploadException();
    }
  }
}
