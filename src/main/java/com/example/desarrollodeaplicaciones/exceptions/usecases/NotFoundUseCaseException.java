package com.example.desarrollodeaplicaciones.exceptions.usecases;

public class NotFoundUseCaseException extends RuntimeException {
  public NotFoundUseCaseException(String msg) {
    super(msg);
  }
}
