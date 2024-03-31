package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class RateDTO {
  private Long userId;

  @DecimalMin(value = "0.0", message = "El puntaje debe ser mayor o igual que cero")
  @DecimalMax(value = "5.0", message = "El puntaje debe ser menor o igual que cinco")
  private Double score;
}
