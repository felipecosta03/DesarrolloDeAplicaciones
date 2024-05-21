package com.example.desarrollodeaplicaciones.exceptions;

public class ForbiddenUseCaseException extends RuntimeException {
  public ForbiddenUseCaseException(String msg) {
    super(msg);
  }
}
