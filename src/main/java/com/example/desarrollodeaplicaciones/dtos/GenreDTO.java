package com.example.desarrollodeaplicaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GenreDTO {
  private int id;
  private String name;
}
