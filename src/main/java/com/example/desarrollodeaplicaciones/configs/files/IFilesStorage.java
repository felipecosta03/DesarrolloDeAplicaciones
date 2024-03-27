package com.example.desarrollodeaplicaciones.configs.files;

import java.io.ByteArrayInputStream;

public interface IFilesStorage {
  String uploadFile(String container, String blobName, ByteArrayInputStream fileStream);

  void deleteFile(String container, String blobName);
}
