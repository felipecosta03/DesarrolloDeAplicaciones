package com.example.desarrollodeaplicaciones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSimpleDto {
  private Long id;
  private String title;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("backdrop_path")
  private String backdropPath;

  @JsonProperty("release_date")
  private String releaseDate;

  @JsonProperty("genre_ids")
  private List<Integer> genreIds;

  @JsonProperty("vote_average")
  private double voteAverage;

  private double popularity;

  @JsonProperty("vote_count")
  private double voteCount;
}
