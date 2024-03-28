package com.example.desarrollodeaplicaciones.configs.files;

import com.example.desarrollodeaplicaciones.models.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IFilesStorage {
  Image uploadFile(MultipartFile file);

  void deleteFile(String id);
}
