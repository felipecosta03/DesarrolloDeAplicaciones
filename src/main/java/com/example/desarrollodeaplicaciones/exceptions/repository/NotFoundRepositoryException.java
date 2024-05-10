package com.example.desarrollodeaplicaciones.exceptions.repository;

public class NotFoundRepositoryException extends RuntimeException{
  public NotFoundRepositoryException(String msg) {
    super(msg);
  }
}
