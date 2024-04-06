package com.example.desarrollodeaplicaciones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeopleCrewDTO {
  private Long id;

  @JsonProperty("known_for_department")
  private String knownForDepartment;

  private String name;

  @JsonProperty("profile_path")
  private String profilePath;

  private String job;
}
