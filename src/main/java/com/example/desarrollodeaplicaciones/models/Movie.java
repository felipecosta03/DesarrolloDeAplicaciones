package com.example.desarrollodeaplicaciones.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String subtitle;
  private String synapsis;
  private String genre;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Media> images;

  @OneToOne(cascade = CascadeType.ALL)
  private Media trailer;

  private String releasedDate;
  private String duration;
  private String director;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Actor> actors;
}
