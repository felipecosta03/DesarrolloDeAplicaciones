package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PeopleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.PersonNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.People;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IPersonRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements IPersonService {
  private final IPersonRepository personRepository;
  private final IMovieRepository movieRepository;

  public PersonService(IPersonRepository personRepository, IMovieRepository movieRepository) {
    this.personRepository = personRepository;
    this.movieRepository = movieRepository;
  }

  @Override
  public List<PeopleDTO> getAll() {
    return personRepository.findAll().stream()
        .map(Mapper::peopleToPeopleDto)
        .collect(Collectors.toList());
  }

  @Override
  public PeopleDTO getById(Long id) {
    return Mapper.peopleToPeopleDto(getPersonById(id));
  }

  @Override
  public StatusDTO add(PeopleDTO person) {
    personRepository.save(Mapper.peopleDtoToPeople(person));
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO delete(Long id) {
    People people = getPersonById(id);
    List<Movie> movies = movieRepository.findByPeople(people);
    for (Movie movie : movies) {
      movie.getActors().remove(people);
      if (movie.getDirector().equals(people)) {
        movie.setDirector(null);
      }
      movieRepository.save(movie);
    }
    personRepository.deleteById(id);
    return StatusDTO.builder().status(200).build();
  }

  private People getPersonById(Long id) {
    return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
  }
}
