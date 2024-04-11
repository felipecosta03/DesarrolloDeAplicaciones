package com.example.desarrollodeaplicaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSimpleDTO {
  private Long id;
  private String title;
  private String subtitle;
  private String synapsis;
  private String posterPath;
}
