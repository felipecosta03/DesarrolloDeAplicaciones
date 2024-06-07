package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
@Schema(name = "User")
public class UserUpdateDto {
  @NotEmpty(message = "Es necesario ingresar un apodo")
  private String nickName;

  private String image;
}
