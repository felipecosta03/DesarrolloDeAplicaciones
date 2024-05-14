package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
@Schema(name = "User")
public class UserDto {
  @NotNull(message = "Es necesario ingresar un id")
  private Long id;

  @NotEmpty(message = "Es necesario ingresar un nombre")
  private String name;

  @NotEmpty(message = "Es necesario ingresar un apellido")
  private String lastName;

  @NotEmpty(message = "Es necesario ingresar un apodo")
  private String nickName;

  @Email(message = "El correo electrónico debe tener un formato válido")
  @NotEmpty(message = "Es necesario ingresar un correo electrónico")
  private String email;

  private ImageDto image;

  private boolean active;
  private List<Long> favoriteMovies;
}