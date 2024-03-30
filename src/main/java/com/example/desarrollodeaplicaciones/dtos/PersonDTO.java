package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
  private Long id;

  @NotEmpty(message = "Es necesario ingresar un nombre completo")
  private String fullName;
}
