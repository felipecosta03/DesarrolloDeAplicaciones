package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieService implements IMovieService {

  private final IMovieRepository movieRepository;

  public MovieService(IMovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public MovieDTO add(MovieDTO movie) {
    movieRepository.save(Mapper.movieDtoToMovie(movie));
    return movie;
  }

  public List<MovieDTO> getAll() {
    return movieRepository.findAll().stream().map(Mapper::movieToMovieDTO).toList();
  }

  public MovieDTO findById(Long id) {
    return Mapper.movieToMovieDTO(
        movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
  }

  @Override
  public StatusDTO update(Long id, MovieDTO movie) {
    return null;
  }
}
