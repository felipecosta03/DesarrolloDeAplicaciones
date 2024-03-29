package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorCodeDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.ErrorMessageValidationDTO;
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
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(MovieNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> movieNotFoundException(MovieNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessageDTO> handleConstraintViolationException(
      ConstraintViolationException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;

    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessageValidationDTO> handleConstraintViolationException(
      MethodArgumentNotValidException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;
    List<String> errors = new ArrayList<>();

    for (ObjectError error : exception.getAllErrors()) {
      errors.add(error.getDefaultMessage());
    }

    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageValidationDTO.builder()
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .messages(errors)
                .build());
  }

  @ExceptionHandler(UserImageNotFound.class)
  public ResponseEntity<ErrorMessageDTO> userImageNotFound(UserImageNotFound exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(ImageDeleteException.class)
  public ResponseEntity<ErrorMessageDTO> imageDeleteException(ImageDeleteException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(ImageUploadException.class)
  public ResponseEntity<ErrorMessageDTO> imageUploadException(ImageUploadException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }
}
