package com.example.desarrollodeaplicaciones.router.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {
  private String error;

  private String message;

  private Integer status;
}
