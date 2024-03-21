package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorCode;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalController {
  @ExceptionHandler
  public ResponseEntity<ErrorMessageDTO> userNotFoundException(UserNotFoundException exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
  
  @ExceptionHandler(MovieNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> movieNotFoundException(MovieNotFoundException exception) {
	  ErrorCode errorCode = ErrorCode.NOT_FOUND;
	  return ResponseEntity.status(errorCode.getStatus())
		  .body( ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
  
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessageDTO> handleConstraintViolationException(ConstraintViolationException exception) {
      ErrorCode errorCode = ErrorCode.BAD_REQUEST;
      
      return ResponseEntity.status(errorCode.getStatus())
		  .body( ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
}
