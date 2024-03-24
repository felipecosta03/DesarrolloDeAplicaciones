package com.example.desarrollodeaplicaciones.models;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private List<String> images;
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	@ManyToMany
	private List<Actor> actors;
}
