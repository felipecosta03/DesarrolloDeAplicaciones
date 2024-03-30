package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.PersonNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Person;
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
  public List<PersonDTO> getAll() {
    return personRepository.findAll().stream()
        .map(Mapper::personToPersonDto)
        .collect(Collectors.toList());
  }

  @Override
  public PersonDTO getById(Long id) {
    return Mapper.personToPersonDto(getPersonById(id));
  }

  @Override
  public StatusDTO add(PersonDTO person) {
    personRepository.save(Mapper.personDtoToPerson(person));
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO delete(Long id) {
    Person person = getPersonById(id);
    List<Movie> movies = movieRepository.findByPersons(person);
    for (Movie movie : movies) {
      movie.getActors().remove(person);
      if (movie.getDirector().equals(person)) {
        movie.setDirector(null);
      }
      movieRepository.save(movie);
    }
    personRepository.deleteById(id);
    return StatusDTO.builder().status(200).build();
  }

  private Person getPersonById(Long id) {
    return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
  }
}
