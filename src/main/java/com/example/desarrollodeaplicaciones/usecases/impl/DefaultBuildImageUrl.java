package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.usecases.BuildImageUrl;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildImageUrl implements BuildImageUrl {

  private static final String IMAGE_URL_500 = "https://image.tmdb.org/t/p/w500%s";

  @Override
  public String apply(String imageId) {
    if (Strings.isNullOrEmpty(imageId)) {
      return null;
    } else {
      return String.format(IMAGE_URL_500, imageId);
    }
  }
}
