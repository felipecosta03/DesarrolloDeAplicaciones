package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "ErrorMessageValidation", description = "Error message validation")
public class ErrorMessageValidationDTO {
  List<String> messages;
  private int status;
  private ErrorCodeDTO code;
}
