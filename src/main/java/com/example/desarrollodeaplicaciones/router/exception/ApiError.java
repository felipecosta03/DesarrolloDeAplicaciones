package com.example.desarrollodeaplicaciones.router.exception;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class ApiError {
  private String error;

  private String message;

  private Integer status;
}
