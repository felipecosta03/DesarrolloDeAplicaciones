package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorCodeDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMultipleMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalController {
  @ExceptionHandler
  public ResponseEntity<ErrorMessageDTO> userNotFoundException(UserNotFoundException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorMultipleMessageDTO> userRegistrationException(UserRegistrationException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.CONFLICT;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMultipleMessageDTO.builder()
                .messages(exception.getMessages())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorMessageDTO> emailAlreadyExistsException(EmailAlreadyExistsException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.CONFLICT;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
}
