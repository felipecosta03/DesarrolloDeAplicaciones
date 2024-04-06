package com.example.desarrollodeaplicaciones.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
  @Id
  private Long id;

  private String title;
  private String subtitle;
  private String synapsis;
  @ElementCollection
  private List<String> genres;
  @ElementCollection
  private List<String> images;

  private String trailer;

  private LocalDate releaseDate;
  private Integer duration;

  @ManyToOne(cascade = CascadeType.ALL)
  private People director;
  private String posterPath;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<People> actors;

  private Double rateAverage;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Rate> rates;
}
