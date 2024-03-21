package com.example.desarrollodeaplicaciones.services;

import java.util.List;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;

public interface IMovieService {
	
	public MovieDTO add(MovieDTO movie);
	public List<MovieDTO> getAll();
	public MovieDTO findById(String id);
}
