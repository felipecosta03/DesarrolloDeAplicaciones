package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieImportDTO {

	private String id;
	private String title;
	private String original_title;
	private String overview;
	private String release_date;
	

}
