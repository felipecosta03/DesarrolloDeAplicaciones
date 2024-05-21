package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SessionDto {
  private String id;
  private String sessionToken;
  private String refreshToken;
}
