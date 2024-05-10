package com.example.desarrollodeaplicaciones.exceptions.repository;

public class BadRequestRepositoryException extends RuntimeException{
  public BadRequestRepositoryException(String msg) {
    super(msg);
  }
}
