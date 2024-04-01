package com.example.desarrollodeaplicaciones.importadata.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.fasterxml.jackson.databind.JsonNode;

public class ImportDataMapper {
	private ImportDataMapper() {}
	
	public static MovieCreationDTO importDataJsonMovieToMovie(JsonNode jsonMovie) {
		return MovieCreationDTO.builder()
				.title(jsonMovie.get("title").asText())
    		.subtitle(jsonMovie.get("original_title").asText())
    		.synapsis(jsonMovie.get("overview").asText())
    		.releaseDate(LocalDate.parse(jsonMovie.get("release_date").asText(),DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    		.director(Long.valueOf(2))
    		.actors(new ArrayList<>())
    		.build();
	}

	public static PersonDTO importDataActorToPerson(JsonNode jsonMovie) {
		return PersonDTO.builder()
				.fullName(jsonMovie.get("name").asText())
				.build();
	}

	
	
	
}
