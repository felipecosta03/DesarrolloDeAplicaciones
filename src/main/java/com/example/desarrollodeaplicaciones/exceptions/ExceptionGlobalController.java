package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorCode;
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
  @ExceptionHandler(UserNotFoundException.class)
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
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;

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
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
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

  @ExceptionHandler(UserImageNotExists.class)
  public ResponseEntity<ErrorMessageDTO> userImageNotExists(UserImageNotExists exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(ImageDeleteException.class)
  public ResponseEntity<ErrorMessageDTO> imageDeleteException(ImageDeleteException exception) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(ImageUploadException.class)
  public ResponseEntity<ErrorMessageDTO> imageUploadException(ImageUploadException exception) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(RateNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> rateNotFoundException(RateNotFoundException exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(InvalidOrderParamException.class)
  public ResponseEntity<ErrorMessageDTO> invalidOrderParamException(
      InvalidOrderParamException exception) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(ImageNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> imageNotFoundException(ImageNotFoundException exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(TrailerNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> trailerNotFoundException(
      TrailerNotFoundException exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(FavoriteMovieAlreadyExistsException.class)
  public ResponseEntity<ErrorMessageDTO> favoriteAlreadyExistsException(
      FavoriteMovieAlreadyExistsException exception) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler(FavoriteMovieNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> favoriteMovieNotFoundException(
      FavoriteMovieNotFoundException exception) {
    ErrorCode errorCode = ErrorCode.NOT_FOUND;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(exception.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }

  @ExceptionHandler({BadRequestEndpointException.class})
  public ResponseEntity<ErrorMessageDTO> badRequestException(final Exception e) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    return ResponseEntity.status(errorCode.getStatus())
        .body(
            ErrorMessageDTO.builder()
                .message(e.getMessage())
                .status(errorCode.getStatus())
                .code(errorCode)
                .build());
  }
}
