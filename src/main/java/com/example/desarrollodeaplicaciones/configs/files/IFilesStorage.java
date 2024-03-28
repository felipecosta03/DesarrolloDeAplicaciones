package com.example.desarrollodeaplicaciones.configs.files;

import com.example.desarrollodeaplicaciones.models.Media;
import org.springframework.web.multipart.MultipartFile;

public interface IFilesStorage {
  Media uploadFile(MultipartFile file);

  void deleteFile(String id);
}
