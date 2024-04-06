package com.example.desarrollodeaplicaciones.dtos.moviesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeopleCastApiDTO {
    private Long id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    @JsonProperty("profile_path")
    private String profilePath;
    private String character;
    @JsonProperty("credit_id")
    private String creditId;
}
