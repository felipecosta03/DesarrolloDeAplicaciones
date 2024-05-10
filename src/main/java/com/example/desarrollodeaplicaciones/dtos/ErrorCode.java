package com.example.desarrollodeaplicaciones.dtos;

import lombok.Getter;

@Getter
public enum ErrorCode {
  NOT_FOUND(404),
  CONFLICT(409),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  FORBIDDEN(403),
  METHOD_NOT_ALLOWED(405),
  INTERNAL_SERVER_ERROR(500);

  private final Integer status;

  ErrorCode(Integer status) {
    this.status = status;
  }
}
