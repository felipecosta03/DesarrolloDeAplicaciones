package com.example.desarrollodeaplicaciones.dtos;

import com.example.desarrollodeaplicaciones.models.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDetailDto {
  private Long id;

  @ManyToMany private List<GenreDto> genres;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  private String overview;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("release_date")
  private String releaseDate;

  private int runtime;
  private String tagline;
  private String title;

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("vote_count")
  private int voteCount;

  @JsonProperty("user_vote")
  private int userVote;

  @JsonProperty("backdrop_path")
  private String backdropPath;

  private double popularity;
  @OneToMany private List<MovieImageDto> images;
  @OneToMany private List<MovieVideoDto> videos;
  @ManyToOne private PeopleCrewDto director;
  @ManyToMany private List<PeopleCastDto> cast;

  @JsonProperty("is_favorite")
  private boolean isFavorite;

  private List<Vote> votes;

}
