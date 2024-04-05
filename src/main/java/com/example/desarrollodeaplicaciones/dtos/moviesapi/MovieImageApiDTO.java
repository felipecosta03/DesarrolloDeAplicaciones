package com.example.desarrollodeaplicaciones.dtos.moviesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieImageApiDTO {
    @JsonProperty("aspect_ratio")
    private double aspectRatio;
    private int height;
    @JsonProperty("iso_639_1")
    private String iso6391;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    private int width;
}
