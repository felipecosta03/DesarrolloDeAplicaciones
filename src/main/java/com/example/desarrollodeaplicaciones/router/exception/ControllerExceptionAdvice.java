package com.example.desarrollodeaplicaciones.router.exception;

import com.example.desarrollodeaplicaciones.exceptions.ForbiddenUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.router.BadRequestRouterException;
import com.example.desarrollodeaplicaciones.exceptions.router.NotFoundRouterException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.FailedDependencyUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.InternalServerErrorUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

  @ExceptionHandler({
    NotFoundRouterException.class,
    NotFoundRepositoryException.class,
    NotFoundUseCaseException.class
  })
  public ResponseEntity<ApiError> notFoundException(Exception e) {
    log.error(e.getMessage());
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .build();
    System.out.println(e);
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler({BadRequestRouterException.class, BadRequestUseCaseException.class})
  public ResponseEntity<ApiError> badRequestException(Exception e) {
    log.error(e.getMessage());

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
    log.error(e.getMessage());
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.FAILED_DEPENDENCY.value())
            .build();
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler({InternalServerErrorUseCaseException.class})
  public ResponseEntity<ApiError> internalServerErrorException(Exception e) {
    log.error(e.getMessage());
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  @ExceptionHandler({ForbiddenUseCaseException.class})
  public ResponseEntity<ApiError> forbiddenException(Exception e) {
    log.error(e.getMessage());
    final ApiError apiError =
        ApiError.builder()
            .error(e.getClass().getSimpleName())
            .message(e.getMessage())
            .status(HttpStatus.FORBIDDEN.value())
            .build();
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }
}
