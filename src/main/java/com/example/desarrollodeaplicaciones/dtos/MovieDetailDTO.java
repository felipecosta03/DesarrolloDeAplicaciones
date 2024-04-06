package com.example.desarrollodeaplicaciones.dtos;

import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieImage;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieVideo;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDetailDTO {
  private Long id;

  private boolean adult;

  private String backdropPath;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<GenreDTO> genres;

  private String homepage;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  private String overview;
  private double popularity;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("release_date")
  private LocalDate releaseDate;

  private int revenue;
  private int runtime;
  private String status;
  private String tagline;
  private String title;

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("vote_count")
  private int voteCount;

  private List<MovieImageDTO> images;

  private List<MovieVideoDTO> videos;

  private PeopleCrewDTO director;

  private List<PeopleCastDTO> cast;
}
