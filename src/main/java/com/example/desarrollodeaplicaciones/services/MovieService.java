package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieService implements IMovieService {

  private final IMovieRepository movieRepository;
  private final IFilesStorage filesStorage;

  public MovieService(IMovieRepository movieRepository, IFilesStorage filesStorage) {
    this.movieRepository = movieRepository;
    this.filesStorage = filesStorage;
  }

  @Override
  public StatusDTO add(MovieDTO movie) {
    movieRepository.save(Mapper.movieDtoToMovie(movie));
    return StatusDTO.builder().status(200).build();
  }

  public List<MovieDTO> getAll() {
    return movieRepository.findAll().stream().map(Mapper::movieToMovieDTO).toList();
  }

  public MovieDTO findById(Long id) {
    return Mapper.movieToMovieDTO(getMovie(id));
  }

  private Movie getMovie(Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @Override
  public StatusDTO updateMovieImage(Long id, MultipartFile image) {
    Movie movie = getMovie(id);
    movie.getImages().add(filesStorage.uploadFile(image));
    movieRepository.save(movie);
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO deleteMovieImage(Long id, String mediaId) {
    Movie movie = getMovie(id);
    boolean isImageRemoved = movie.getImages().removeIf(media -> media.getId().equals(mediaId));
    if (isImageRemoved) {
      movieRepository.save(movie);
      return StatusDTO.builder().status(200).build();
    }
    return StatusDTO.builder().status(400).build();
  }
}
