package com.example.desarrollodeaplicaciones.dtos.moviesapi;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailApiDTO {
  private boolean adult;

  @JsonProperty("backdrop_path")
  private String backdropPath;

  @JsonProperty("belongs_to_collection")
  private Object belongsToCollection;

  private int budget;
  private List<GenreDTO> genres;
  private String homepage;
  private Long id;

  @JsonProperty("imdb_id")
  private String imdbId;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  private String overview;
  private double popularity;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("release_date")
  private String releaseDate;

  private int revenue;
  private int runtime;
  private String status;
  private String tagline;
  private String title;

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("vote_count")
  private int voteCount;

  private List<MovieImageApiDTO> images;
  private List<MovieVideoApiDTO> videos;

  private List<PeopleCrewApiDTO> crew;
  private List<PeopleCastApiDTO> cast;
}
