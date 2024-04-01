package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusPersonDTO;

import java.util.List;

public interface IPersonService {
    List<PersonDTO> getAll();

    PersonDTO getById(Long id);

    StatusPersonDTO add(PersonDTO person);

    StatusDTO delete(Long id);
}
