package com.example.desarrollodeaplicaciones.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MovieSimple")
public class MovieSimpleDTO {
  private Long id;
  private String title;
  private String subtitle;
  private String synapsis;
  private String posterPath;
}
