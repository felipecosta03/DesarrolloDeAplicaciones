package com.example.desarrollodeaplicaciones.dtos;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieVideoDTO {
  @Id private String id;
  private String name;
  private String key;
}
