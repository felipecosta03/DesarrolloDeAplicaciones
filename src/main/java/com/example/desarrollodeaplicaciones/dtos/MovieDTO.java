package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

  private List<String> images;
  private String trailer;
  private String releasedDate;
  private String duration;
  private String director;
  private List<ActorDTO> actors;
}
