package com.example.desarrollodeaplicaciones.exceptions.repository;

public class FailedDependencyRepositoryException extends RuntimeException {
  public FailedDependencyRepositoryException(String msg) {
    super(msg);
  }
}
