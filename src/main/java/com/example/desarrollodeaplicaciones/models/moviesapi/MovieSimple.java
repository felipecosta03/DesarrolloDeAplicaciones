package com.example.desarrollodeaplicaciones.models.moviesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class MovieSimple {
  @Id private Long id;

  @JsonProperty("backdrop_path")
  private String backdropPath;

  private double popularity;

  @JsonProperty("genre_ids")
  @ElementCollection
  private List<Integer> genreIds;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("title")
  private String title;

  @JsonProperty("overview")
  @Column(length = 5000)
  private String overview;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("release_date")
  private LocalDate releaseDate;

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("vote_count")
  private double voteCount;
}
