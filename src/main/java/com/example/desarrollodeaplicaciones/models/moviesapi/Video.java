package com.example.desarrollodeaplicaciones.models.moviesapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Video {
  @Id private String id;
  private String name;

  @Column(name = "urlº")
  private String key;
}
