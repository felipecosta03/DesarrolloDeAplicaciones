package com.example.desarrollodeaplicaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusPersonDTO {
  private int status;
  private PersonDTO personDto;
}
