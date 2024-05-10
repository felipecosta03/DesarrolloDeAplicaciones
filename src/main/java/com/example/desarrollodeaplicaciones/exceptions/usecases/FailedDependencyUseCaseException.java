package com.example.desarrollodeaplicaciones.exceptions.usecases;

public class FailedDependencyUseCaseException extends RuntimeException{
  public FailedDependencyUseCaseException(String msg) {
    super(msg);
  }
}
