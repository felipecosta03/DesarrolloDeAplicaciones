package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.PeopleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import java.util.List;

public interface IPersonService {
  List<PeopleDTO> getAll();

  PeopleDTO getById(Long id);

  StatusDTO add(PeopleDTO person);

  StatusDTO delete(Long id);
}
