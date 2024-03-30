package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.InvalidOrderParamException;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.PersonNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Person;
import com.example.desarrollodeaplicaciones.repositories.IMoviePageableRepository;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IPersonRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieService implements IMovieService {

  private static final String ORDER_ASC = "asc";
  private static final String ORDER_DESC = "desc";
  private static final String RELEASE_DATE = "releaseDate";
  private static final String QUALIFICATION = "qualification";
  private final IMovieRepository movieRepository;
  private final IFilesStorage filesStorage;
  private final IPersonRepository personRepository;
  private final IMoviePageableRepository moviePageableRepository;

  public MovieService(
      IMovieRepository movieRepository,
      IFilesStorage filesStorage,
      IPersonRepository personRepository,
      IMoviePageableRepository moviePageableRepository) {
    this.movieRepository = movieRepository;
    this.filesStorage = filesStorage;
    this.personRepository = personRepository;
    this.moviePageableRepository = moviePageableRepository;
  }

  @Override
  public StatusDTO add(MovieCreationDTO movie) {
    Movie newMovie = Mapper.movieCreationDtoToMovie(movie);
    newMovie.setDirector(getPersonById(movie.getDirector()));
    newMovie.setActors(
        movie.getActors().stream().map(this::getPersonById).collect(Collectors.toList()));
    movieRepository.save(newMovie);
    return StatusDTO.builder().status(200).build();
  }

  public List<MovieDTO> getAll(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> genre,
      Optional<Integer> page) {
    PageRequest pageRequest = getPageRequest(page, dateOrder, qualificationOrder);
    return genre
        .map(
            genreName ->
                moviePageableRepository.findAllByGenre(pageRequest, genreName).stream()
                    .map(Mapper::movieToMovieDTO)
                    .toList())
        .orElseGet(
            () ->
                moviePageableRepository.findAll(pageRequest).stream()
                    .map(Mapper::movieToMovieDTO)
                    .toList());
  }

  @Override
  public List<MovieDTO> getAllByTitleOrActor(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> value,
      Optional<Integer> page) {
    PageRequest pageRequest = getPageRequest(page, dateOrder, qualificationOrder);
    return moviePageableRepository.findAllByTitleOrActor(pageRequest, value.orElse("")).stream()
        .map(Mapper::movieToMovieDTO)
        .toList();
  }

  private PageRequest getPageRequest(
      Optional<Integer> page, Optional<String> dateOrder, Optional<String> qualificationOrder) {
    return PageRequest.of(page.orElse(0), 10, getSort(dateOrder, qualificationOrder));
  }

  private Sort getSort(Optional<String> dateOrder, Optional<String> qualificationOrder) {
    Sort sort = Sort.by(Sort.Order.desc(RELEASE_DATE));
    if (dateOrder.isPresent()) {
      if (dateOrder.get().equalsIgnoreCase(ORDER_ASC)) {
        sort = Sort.by(Sort.Order.asc(RELEASE_DATE));
      } else if (dateOrder.get().equalsIgnoreCase(ORDER_DESC)) {
        sort = Sort.by(Sort.Order.desc(RELEASE_DATE));
      } else {
        throw new InvalidOrderParamException(RELEASE_DATE);
      }
    }

    if (qualificationOrder.isPresent()) {
      if (qualificationOrder.get().equalsIgnoreCase(ORDER_ASC)) {
        sort = sort.and(Sort.by(Sort.Order.asc(QUALIFICATION)));
      } else if (qualificationOrder.get().equalsIgnoreCase(ORDER_DESC)) {
        sort = sort.and(Sort.by(Sort.Order.desc(QUALIFICATION)));
      } else {
        throw new InvalidOrderParamException(QUALIFICATION);
      }
    }

    return sort;
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
  public StatusDTO update(Long id, MovieCreationDTO movie) {
    Movie movieAux = getMovie(id);
    Movie movieToUpdate = Mapper.movieCreationDtoToMovie(movie);
    movieToUpdate.setId(id);
    movieToUpdate.setImages(movieAux.getImages());
    movieToUpdate.setTrailer(movieAux.getTrailer());
    movieToUpdate.setDirector(getPersonById(movie.getDirector()));
    movieToUpdate.setActors(
        movie.getActors().stream().map(this::getPersonById).collect(Collectors.toList()));
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
        .orElseThrow(() -> new PersonNotFoundException(personId));
  }
}
