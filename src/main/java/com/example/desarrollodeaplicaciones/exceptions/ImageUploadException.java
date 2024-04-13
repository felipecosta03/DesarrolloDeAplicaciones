package com.example.desarrollodeaplicaciones.exceptions;

public class ImageUploadException extends RuntimeException {
  public ImageUploadException() {
    super("Error uploading image");
  }
}
