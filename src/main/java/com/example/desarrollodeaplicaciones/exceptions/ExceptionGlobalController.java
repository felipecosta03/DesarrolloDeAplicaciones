package com.example.desarrollodeaplicaciones.exceptions;

import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice public class ExceptionGlobalController {
    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(404).body(ErrorMessageDTO.builder().message(exception.getMessage()).status(404).build());
    }
}
