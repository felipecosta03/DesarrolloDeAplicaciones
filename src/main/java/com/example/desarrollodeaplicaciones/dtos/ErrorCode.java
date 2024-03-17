package com.example.desarrollodeaplicaciones.dtos;

import lombok.Getter;

@Getter
public enum ErrorCode {
  NOT_FOUND(404);

  private Integer status;

  ErrorCode(Integer status) {
    this.status = status;
  }
}
