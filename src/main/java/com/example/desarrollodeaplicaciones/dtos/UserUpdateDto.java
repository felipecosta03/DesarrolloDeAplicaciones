package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
@Schema(name = "User")
public class UserUpdateDto {
  @NotNull(message = "Es necesario ingresar un id")
  private Long id;

  @NotEmpty(message = "Es necesario ingresar un apodo")
  private String nickName;

  private String image;
}
