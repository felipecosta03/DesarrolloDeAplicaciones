package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements IMovieService{

	private final IMovieRepository movieRepository;

	public MovieServiceImpl(IMovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public MovieDTO add(MovieDTO movie) {
		movieRepository.save(Mapper.movieDtoToMovie(movie));
		return movie;
	}

	public List<MovieDTO> getAll() {
		return Mapper.listMoviesToArrayListMoviesDTO(movieRepository.findAll());

	}

	public MovieDTO findById(Long id) {
		return Mapper.movieToMovieDTO(
			movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
	}


}
