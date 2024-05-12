package com.example.desarrollodeaplicaciones.models.moviesapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Actor {

  @Id private Long id;
  private String name;

  @JsonProperty("known_for")
  @ManyToMany(cascade = CascadeType.ALL)
  private List<MovieSimple> knownFor;
}
