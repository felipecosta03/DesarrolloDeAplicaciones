package com.example.desarrollodeaplicaciones.dtos;

import lombok.Getter;

@Getter
public enum MovieParameterDTO {
  DESCENDENT("desc"),
  ASCENDENT("asc");

  private String value;

  MovieParameterDTO(String value) {
    this.value = value;
  }
}
