package com.example.desarrollodeaplicaciones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PeopleCast")
public class PeopleCastDto {
  private Long id;

  @JsonProperty("known_for_department")
  private String knownForDepartment;

  private String name;

  @JsonProperty("profile_path")
  private String profilePath;

  private String character;
}
