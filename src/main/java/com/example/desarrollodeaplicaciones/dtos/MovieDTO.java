package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
public class MovieDTO {
	@NotEmpty
	private String title;
	@NotEmpty(message = "Es necesario ingresar un subtítulo")
	private String subtitle;
	@NotEmpty(message = "Es necesario ingresar una sinópsis")
	private String synapsis;
	@NotEmpty(message = "Es necesario ingresar un género")
	private String genre;
	private List<String> image;
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	private List<ActorDTO> actors;


}
