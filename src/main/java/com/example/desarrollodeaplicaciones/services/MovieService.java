package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import java.util.stream.Collectors;

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
    return movieRepository.findAll().stream().map(Mapper::movieToMovieDTO).collect(Collectors.toList());
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
      filesStorage.deleteFile(mediaId);
      return StatusDTO.builder().status(200).build();
    }
    return StatusDTO.builder().status(400).build();
  }

  @Override
  public StatusDTO updateMovieTrailer(Long id, MultipartFile image) {
    Movie movie = getMovie(id);
    if (movie.getTrailer() != null) {
      deleteTrailerFromMovie(movie);
    }
    movie.setTrailer(filesStorage.uploadFile(image));
    movieRepository.save(movie);
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO deleteMovieTrailer(Long id) {
    Movie movie = getMovie(id);
    if (movie.getTrailer() == null) {
      return StatusDTO.builder().status(400).build();
    }
    deleteTrailerFromMovie(movie);
    return StatusDTO.builder().status(200).build();
  }

  private void deleteTrailerFromMovie(Movie movie) {
    filesStorage.deleteFile(movie.getTrailer().getId());
    movie.setTrailer(null);
    movieRepository.save(movie);
  }

  @Override
  public StatusDTO update(Long id, MovieSimpleDTO movie) {
    Movie movieToUpdate = getMovie(id);
    movieToUpdate.setTitle(movie.getTitle());
    movieToUpdate.setReleaseDate(movie.getReleaseDate());
    movieToUpdate.setDuration(movie.getDuration());
    movieToUpdate.setDirector(Mapper.personDtoToPerson(movie.getDirector()));
    movieToUpdate.setActors(movie.getActors().stream().map(Mapper::personDtoToPerson).collect(Collectors.toList()));
    movieToUpdate.setGenre(movie.getGenre());
    movieToUpdate.setSubtitle(movie.getSubtitle());
    movieToUpdate.setSynapsis(movie.getSynapsis());
    movieRepository.save(movieToUpdate);
    return StatusDTO.builder().status(200).build();
  }
}
