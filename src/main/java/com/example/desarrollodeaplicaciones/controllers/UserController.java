package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.services.IUserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<UserDTO> add(@RequestBody UserDTO user) {
    return ResponseEntity.status(200).body(userService.add(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.findById(id));
  }

  @PostMapping("/{id}/image")
  public ResponseEntity<StatusDTO> updateImage(
      @PathVariable Long id, @RequestParam("image") MultipartFile image) {
    return ResponseEntity.status(200).body(userService.updateUserImage(id, image));
  }

  @DeleteMapping("/{id}/image")
  public ResponseEntity<StatusDTO> deleteImage(
          @PathVariable Long id) {
    return ResponseEntity.status(200).body(userService.deleteImage(id));
  }
}
