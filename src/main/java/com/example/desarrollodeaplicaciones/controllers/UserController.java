package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.services.IUserService;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.status(200).body(userService.getAll());
  }

  @PostMapping
  public ResponseEntity<StatusDTO> add(@RequestBody UserDTO user) {
    StatusDTO statusDTO = userService.add(user);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PutMapping
  public ResponseEntity<StatusDTO> update(@Valid @RequestBody UserDTO user) {
    StatusDTO statusDTO = userService.update(user);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.findById(id));
  }

  @PatchMapping("/{id}/image")
  public ResponseEntity<StatusDTO> updateImage(
      @PathVariable Long id, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = userService.updateUserImage(id, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}/image")
  public ResponseEntity<StatusDTO> deleteImage(@PathVariable Long id) {
    StatusDTO statusDTO = userService.deleteImage(id);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
