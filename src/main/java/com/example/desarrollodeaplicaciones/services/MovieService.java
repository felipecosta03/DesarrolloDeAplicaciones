package com.example.desarrollodeaplicaciones.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;

@Service
public class MovieService implements IMovieService{

	private final IMovieRepository movieRepository;

	public MovieService(IMovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public MovieDTO add(MovieDTO movie) {
		movieRepository.insert(Mapper.movieDtoToMovie(movie));
		return movie;
	}

	public List<MovieDTO> getAll() {
		return Mapper.listMoviesToArrayListMoviesDTO(movieRepository.findAll());

	}

	public MovieDTO findById(String id) {
		return Mapper.movieToMovieDTO(
			movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
	}


}
