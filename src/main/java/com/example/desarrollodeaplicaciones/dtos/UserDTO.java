package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
public class UserDTO {
    @NotEmpty(message = "Debe tener un id")
    private String id;
    @NotEmpty(message = "Debe ingresar un nombre")
    private String name;
    @Size(min = 3, max = 10, message = "El apellido debe tener un largo de maximo 10 y minimo 3 caracteres")
    private String lastName;
}
