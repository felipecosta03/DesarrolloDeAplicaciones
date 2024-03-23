package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorMultipleMessageDTO {
  private List<String> messages;
  private int status;
  private ErrorCodeDTO code;
}
