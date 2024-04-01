package com.example.desarrollodeaplicaciones.importadata.services;

import lombok.Getter;

@Getter
public enum ImportDataResources {
	BASE_URL("https://api.themoviedb.org/3"),
	BASE_URL_IMAGE("https://image.tmdb.org/t/p/w500"),
	RESOURCE_AUTHENTICATION("/authentication"),
	POPULAR("/movie/popular?language=en-US&page="), //Add the page to the end of the String
	TOKEN("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZDRiMWVlMzRlN2IyNjM2ODU4NTVmNTljZjFiNDQ2NiIsInN1YiI6IjY2MDc3ZDdlMmZhZjRkMDE3ZGM4ZTVkMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qjn7RUDgpL-dF0i-a8OINhl3uqRH1tFE_LzOo1h_6Ao"),
	DETAILS("/movie/movie_id?language=en-US"),  //Replace "movie_id" with the real value
	ACTORS("/movie/movie_id/credits?language=en-US"),
	IMAGES("/movie/movie_id/images");
	private final String resources;
	
	ImportDataResources(String resources) {
		this.resources = resources;
	}

	
}
