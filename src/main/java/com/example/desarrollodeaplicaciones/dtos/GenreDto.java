package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {
  private Integer id;
  private String name;
}
