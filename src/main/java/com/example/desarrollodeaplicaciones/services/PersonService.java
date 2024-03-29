package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.PersonNotFoundException;
import com.example.desarrollodeaplicaciones.models.Person;
import com.example.desarrollodeaplicaciones.repositories.IPersonRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService{
    private IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream().map(Mapper::personToPersonDto).collect(Collectors.toList();
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

    private Person getPersonById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }


}
