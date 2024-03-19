package com.example.desarrollodeaplicaciones.services;

import org.springframework.stereotype.Service;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;

@Service
public class MovieServiceImpl implements IMovieService{

	private final IMovieRepository movieRepository;
	
	public MovieServiceImpl(IMovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public MovieDTO add(MovieDTO movie) {
		movieRepository.insert(Mapper.movieDtoToUser(movie));
		return movie;
	}
	

	
}
