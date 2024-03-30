package com.example.desarrollodeaplicaciones.exceptions;

public class InvalidOrderParamException extends RuntimeException {
  public InvalidOrderParamException(String field) {
    super("Invalid order parameter: " + field);
  }
}
