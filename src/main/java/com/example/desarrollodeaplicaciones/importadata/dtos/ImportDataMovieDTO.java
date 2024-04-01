package com.example.desarrollodeaplicaciones.importadata.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImportDataMovieDTO {
	private Long id;
	private String title;
	private String originTitle;
	private String overview;
	private String releaseDate;
}
