package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Valid
public class MovieSimpleDTO {
  private Long id;
  @NotEmpty(message = "Es necesario ingresar un título")
  private String title;

  @NotEmpty(message = "Es necesario ingresar un subtítulo")
  private String subtitle;

  @NotEmpty(message = "Es necesario ingresar una sinópsis")
  private String synapsis;

  @NotEmpty(message = "Es necesario ingresar un género")
  private String genre;

  @NotNull(message = "Es necesario ingresar una fecha de lanzamiento")
  private LocalDate releaseDate;
  @NotNull(message = "Es necesario ingresar una duración")
  @Positive(message = "La duración debe ser mayor a 0")
  private Integer duration;
  @NotNull(message = "Es necesario ingresar un director")
  private PersonDTO director;
  private List<PersonDTO> actors;
}