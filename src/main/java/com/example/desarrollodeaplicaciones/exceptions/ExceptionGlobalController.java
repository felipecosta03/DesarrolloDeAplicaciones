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

  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> personNotFoundException(
      PersonNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(RateNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> rateNotFoundException(RateNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(InvalidOrderParamException.class)
  public ResponseEntity<ErrorMessageDTO> invalidOrderParamException(
      InvalidOrderParamException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(RateAlreadyExistsException.class)
  public ResponseEntity<ErrorMessageDTO> rateAlreadyExistsException(
      RateAlreadyExistsException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(ImageNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> imageNotFoundException(ImageNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(TrailerNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> trailerNotFoundException(
      TrailerNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(FavoriteMovieAlreadyExistsException.class)
  public ResponseEntity<ErrorMessageDTO> favoriteAlreadyExistsException(
      FavoriteMovieAlreadyExistsException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.BAD_REQUEST;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
                .build());
  }

  @ExceptionHandler(FavoriteMovieNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> favoriteMovieNotFoundException(
      FavoriteMovieNotFoundException exception) {
    ErrorCodeDTO errorCodeDTO = ErrorCodeDTO.NOT_FOUND;
    return ResponseEntity.status(errorCodeDTO.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCodeDTO.getStatus())
                .code(errorCodeDTO)
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
