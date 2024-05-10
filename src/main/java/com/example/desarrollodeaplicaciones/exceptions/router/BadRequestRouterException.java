package com.example.desarrollodeaplicaciones.exceptions.router;

public class BadRequestRouterException extends RuntimeException {
  public BadRequestRouterException(String msg) {
    super(msg);
  }
}
