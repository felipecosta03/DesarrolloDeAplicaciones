package com.example.desarrollodeaplicaciones.exceptions;

public class BadRequestEndpointException extends RuntimeException {

  public BadRequestEndpointException(String msg) {
    super(msg);
  }
}
