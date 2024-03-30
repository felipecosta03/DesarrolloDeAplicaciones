package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import java.util.List;

public interface IPersonService {
  List<PersonDTO> getAll();

  PersonDTO getById(Long id);

  StatusDTO add(PersonDTO person);

  StatusDTO delete(Long id);
}
