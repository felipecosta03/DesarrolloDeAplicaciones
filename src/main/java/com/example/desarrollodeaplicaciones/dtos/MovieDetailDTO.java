package com.example.desarrollodeaplicaciones.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "MovieDetail", description = "Movie detail")
public class MovieDetailDTO {
  private Long id;

  @ManyToMany
  private List<GenreDTO> genres;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  private String overview;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("release_date")
  private LocalDate releaseDate;

  private int runtime;
  private String tagline;
  private String title;

  @JsonProperty("vote_average")
  private double voteAverage;

  @JsonProperty("vote_count")
  private int voteCount;

    @OneToMany
  private List<MovieImageDTO> images;
  @OneToMany
  private List<MovieVideoDTO> videos;
  @ManyToOne
  private PeopleCrewDTO director;
  @ManyToMany
  private List<PeopleCastDTO> cast;
}
