package com.example.desarrollodeaplicaciones.exceptions.usecases;

public class BadRequestUseCaseException extends RuntimeException {
  public BadRequestUseCaseException(String msg) {
    super(msg);
  }
}
