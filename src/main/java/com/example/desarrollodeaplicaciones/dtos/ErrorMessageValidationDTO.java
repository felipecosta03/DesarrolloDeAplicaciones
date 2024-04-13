package com.example.desarrollodeaplicaciones.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageValidationDTO {
  List<String> messages;
  private int status;
  private ErrorCodeDTO code;
}
