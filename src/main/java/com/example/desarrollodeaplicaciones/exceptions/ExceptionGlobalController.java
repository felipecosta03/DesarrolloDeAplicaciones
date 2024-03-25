package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorCodeDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageValidationDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMultipleMessageDTO;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
  public ResponseEntity<ErrorMultipleMessageDTO> userRegistrationException(
      UserRegistrationException exception) {
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
  public ResponseEntity<ErrorMessageDTO> emailAlreadyExistsException(
      EmailAlreadyExistsException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.CONFLICT;
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

    ErrorCodeDTO errorCode = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessageDTO> handleConstraintViolationException(
      ConstraintViolationException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.BAD_REQUEST;

    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessageValidationDTO> handleConstraintViolationException(
      MethodArgumentNotValidException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.BAD_REQUEST;
    List<String> errors = new ArrayList<>();

    for (ObjectError error : exception.getAllErrors()) {
      errors.add(error.getDefaultMessage());
    }

    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageValidationDTO.builder()
                .status(errorCode.getStatus())
                .code(errorCode)
                .messages(errors)
                .build());
  }

  @ExceptionHandler(InactiveUserException.class)
  public ResponseEntity<ErrorMessageDTO> inactiveUserException(InactiveUserException exception) {
    ErrorCodeDTO errorCode = ErrorCodeDTO.BAD_REQUEST;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
}
