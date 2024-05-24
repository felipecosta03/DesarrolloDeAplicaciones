package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.usecases.BuildVideoUrl;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildVideoUrl implements BuildVideoUrl {
  @Override
  public String apply(String videoUrl) {
    return String.format("https://www.youtube.com/watch?v=%s", videoUrl);
  }
}
