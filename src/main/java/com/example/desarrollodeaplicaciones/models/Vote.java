package com.example.desarrollodeaplicaciones.models;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Vote {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Integer score;

  private Long userId;
}
