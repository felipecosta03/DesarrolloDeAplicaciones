package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.services.IPersonService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

  private final IPersonService personService;

  public PersonController(IPersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public ResponseEntity<List<PersonDTO>> findAll() {
    return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(personService.getById(id));
  }

  @PostMapping()
  public ResponseEntity<StatusDTO> add(@RequestBody PersonDTO person) {
    StatusDTO statusDTO = personService.add(person);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {
    StatusDTO statusDTO = personService.delete(id);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
