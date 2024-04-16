package com.example.desarrollodeaplicaciones.models.moviesapi;

import com.example.desarrollodeaplicaciones.models.Genre;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovieDetail {
  @Id private Long id;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Genre> genres;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("original_title")
  private String originalTitle;

  @Column(length = 1000)
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

  @OneToMany(cascade = CascadeType.ALL)
  private List<Image> images;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Video> videos;

  @ManyToOne(cascade = CascadeType.ALL)
  private PeopleCrew director;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<PeopleCast> cast;
}