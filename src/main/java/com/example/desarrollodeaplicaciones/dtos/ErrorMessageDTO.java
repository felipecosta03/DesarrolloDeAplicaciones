package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "ErrorMessage", description = "Error message")
public class ErrorMessageDTO {
  private String message;
  private int status;
  private ErrorCode code;
}
