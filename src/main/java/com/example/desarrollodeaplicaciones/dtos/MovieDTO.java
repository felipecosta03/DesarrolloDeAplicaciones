package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Valid
@Schema(name = "Movie")
public class MovieDTO {
  private Long id;

  @NotEmpty(message = "Es necesario ingresar un título")
  private String title;

  @NotEmpty(message = "Es necesario ingresar un subtítulo")
  private String subtitle;

  @NotEmpty(message = "Es necesario ingresar una sinópsis")
  private String synapsis;

  @NotEmpty(message = "Es necesario ingresar un género")
  private String genre;

  private List<MediaDTO> images;
  private MediaDTO trailer;

  @NotNull(message = "Es necesario ingresar una fecha de lanzamiento")
  private LocalDate releaseDate;

  @NotNull(message = "Es necesario ingresar una duración")
  @Positive(message = "La duración debe ser mayor a 0")
  private Integer duration;

  private PersonDTO director;

  private List<PersonDTO> actors;

  @NotNull(message = "Es necesario ingresar una calificación")
  @PositiveOrZero(message = "La calificación debe ser mayor o igual a 0")
  private Double rateAverage;

  private List<RateDTO> rates;
}
