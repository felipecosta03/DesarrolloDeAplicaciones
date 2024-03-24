package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Data
@Valid
@Builder
public class UserDTO {
  private String id;

  @NotEmpty(message = "Es necesario ingresar un nombre")
  private String name;

  @NotEmpty(message = "Es necesario ingresar un apellido")
  private String lastName;

  @NotEmpty(message = "Es necesario ingresar un apodo")
  private String nickName;

  @Email(message = "El correo electrónico debe tener un formato válido")
  @NotEmpty(message = "Es necesario ingresar un correo electrónico")
  private String email;

  @NotEmpty(message = "Es necesario ingresar una URL de imagen")
  private String imageUrl;

  private boolean active;
  private List<MovieDTO> favoriteMovies = new ArrayList<>();
}
