package com.example.desarrollodeaplicaciones.configs.files;

import org.springframework.web.multipart.MultipartFile;

public interface IFilesStorage {
  String uploadFile(MultipartFile file);

  void deleteFile(String id);

  String uploadImage(String imageUrl);
}
