package com.example.desarrollodeaplicaciones.router.exception;

import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.router.BadRequestRouterException;
import com.example.desarrollodeaplicaciones.exceptions.router.NotFoundRouterException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.FailedDependencyUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

  @ExceptionHandler({NotFoundRouterException.class, NotFoundRepositoryException.class})
  public ResponseEntity<ApiError> notFoundException(Exception e) {
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .build();

    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler({
    BadRequestUseCaseException.class,
    BadRequestRouterException.class,
    BadRequestUseCaseException.class
  })
  public ResponseEntity<ApiError> badRequestException(Exception e) {
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build();

    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler({
    FailedDependencyUseCaseException.class,
    FailedDependencyRepositoryException.class,
    Exception.class
  })
  public ResponseEntity<ApiError> failedDependencyException(Exception e) {
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.FAILED_DEPENDENCY.value())
            .build();

    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }
}
