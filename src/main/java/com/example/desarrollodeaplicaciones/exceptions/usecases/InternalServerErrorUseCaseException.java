package com.example.desarrollodeaplicaciones.exceptions.usecases;

public class InternalServerErrorUseCaseException extends RuntimeException {
  public InternalServerErrorUseCaseException(String msg) {
    super(msg);
  }
}
