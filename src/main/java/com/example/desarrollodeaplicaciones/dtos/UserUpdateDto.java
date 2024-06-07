package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
public class UserUpdateDto {
  @NotEmpty(message = "Es necesario ingresar un apodo")
  private String nickName;

  private String image;
}
