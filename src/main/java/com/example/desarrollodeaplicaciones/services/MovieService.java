package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusMovieDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.models.Media;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Person;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IPersonRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieService implements IMovieService {

  private final IMovieRepository movieRepository;
  private final IFilesStorage filesStorage;
  private final IPersonRepository personRepository;

  public MovieService(
      IMovieRepository movieRepository,
      IFilesStorage filesStorage,
      IPersonRepository personRepository) {
    this.movieRepository = movieRepository;
    this.filesStorage = filesStorage;
    this.personRepository = personRepository;
  }

  @Override
  public StatusMovieDTO add(MovieCreationDTO movie) {
    Movie newMovie = Mapper.movieCreationDtoToMovie(movie);
    newMovie.setDirector(getPersonById(movie.getDirector()));
    newMovie.setActors(
        movie.getActors().stream().map(this::getPersonById).collect(Collectors.toList()));
    movieRepository.save(newMovie);
    return StatusMovieDTO.builder()
    		.status(200)
    		.movieDto(Mapper.movieToMovieDTO(newMovie))
    		.build();
  }

  public List<MovieDTO> getAll() {
    return movieRepository.findAll().stream()
        .map(Mapper::movieToMovieDTO)
        .collect(Collectors.toList());
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
  
  public StatusDTO updateMovieImageFromServer(Long id, byte[] image) {
    Movie movie = getMovie(id);
    Optional.ofNullable(movie)
    	.map(Movie::getImages)
    	.	orElseGet(() -> {
        List<Media> images = new ArrayList<>();
        movie.setImages(images); 
        return images;
    	})
    	.add(filesStorage.uploadFileFromServer(image)); // Agregamos la nueva imagen
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
  public StatusDTO update(Long id, MovieCreationDTO movie) {
    Movie movieAux = getMovie(id);
    Movie movieToUpdate = Mapper.movieCreationDtoToMovie(movie);
    movieToUpdate.setId(id);
    movieToUpdate.setImages(movieAux.getImages());
    movieToUpdate.setTrailer(movieAux.getTrailer());
    movieToUpdate.setDirector(getPersonById(movie.getDirector()));
    movieToUpdate.setActors(
        Optional.ofNullable(movie.getActors())
        .map(
            actors ->
                actors.stream().map(this::getPersonById).collect(Collectors.toList()))
        .orElse(new ArrayList<>()));
    movieRepository.save(movieToUpdate);
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO deleteActor(Long id, Long actorId) {
    Movie movie = getMovie(id);
    boolean isActorRemoved = movie.getActors().removeIf(actor -> actor.getId().equals(actorId));
    if (isActorRemoved) {
      movieRepository.save(movie);
      return StatusDTO.builder().status(200).build();
    }
    return StatusDTO.builder().status(400).build();
  }

  private Person getPersonById(Long personId) {
    return personRepository
        .findById(personId)
        .orElseThrow(() -> new MovieNotFoundException(personId));
  }
}
