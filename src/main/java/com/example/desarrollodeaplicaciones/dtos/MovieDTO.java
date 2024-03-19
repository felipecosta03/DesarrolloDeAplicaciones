package com.example.desarrollodeaplicaciones.dtos;

import java.util.ArrayList;

import com.example.desarrollodeaplicaciones.models.Actor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
public class MovieDTO {
	@NotEmpty(message = "Es necesario ingresar un título")
	private String title;
	@NotEmpty(message = "Es necesario ingresar un subtítulo")
	private String subtitle;
	@NotEmpty(message = "Es necesario ingresar una sinópsis")
	private String synapsis;
	@NotEmpty(message = "Es necesario ingresar un género")
	private String genre;
	private ArrayList<String> image;
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	private ArrayList<Actor> actors;
	
	
}
