package com.example.desarrollodeaplicaciones.dtos;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
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
	@Builder.Default
	private List<String> image = new ArrayList<>();
	private String trailer;
	private String releasedDate;
	private String duration;
	private String director;
	@Builder.Default
	private List<ActorDTO> actors = new ArrayList<>();
}
