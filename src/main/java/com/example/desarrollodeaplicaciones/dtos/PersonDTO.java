package com.example.desarrollodeaplicaciones.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  @NotEmpty(message = "Es necesario ingresar un nombre")
  private String firstName;
  @NotEmpty(message = "Es necesario ingresar un apellido")
  private String lastName;
}
