package com.example.desarrollodeaplicaciones.dtos.moviesapi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieVideoApiDTO {
    @JsonProperty("iso_639_1")
    private String iso6391;
    @JsonProperty("iso_3166_1")
    private String iso31661;
    private String name;
    private String key;
    @JsonProperty("published_at")
    private String publishedAt;
    private String site;
    private int size;
    private String type;
    private boolean official;
    private String id;
}
