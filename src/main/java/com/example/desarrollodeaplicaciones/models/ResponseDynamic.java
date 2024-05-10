package com.example.desarrollodeaplicaciones.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDynamic<T> {
  private int status;
  private T data;
}
