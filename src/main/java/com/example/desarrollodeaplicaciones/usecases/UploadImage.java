package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface UploadImage extends Function<UploadImage.Model, String> {
  @Builder
  @Getter
  class Model {
    private String imageUrl;
  }
}
