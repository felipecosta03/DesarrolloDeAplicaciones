package com.example.desarrollodeaplicaciones.usecases;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface DeleteImage extends Consumer<DeleteImage.Model> {
  @Builder
  @Getter
  class Model {
    private String imageId;
  }
}
