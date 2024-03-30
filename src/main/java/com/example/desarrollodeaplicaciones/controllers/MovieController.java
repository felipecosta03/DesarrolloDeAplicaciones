package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.services.IMovieService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final IMovieService movieService;

  public MovieController(IMovieService movieController) {
    this.movieService = movieController;
  }

  @PostMapping
  public ResponseEntity<StatusDTO> add(@RequestBody @Valid MovieCreationDTO movie) {
    StatusDTO statusDTO = movieService.add(movie);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @GetMapping
  public ResponseEntity<List<MovieDTO>> findAll() {
    return ResponseEntity.status(200).body(movieService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
    return ResponseEntity.status(200).body(movieService.findById(id));
  }

  @PatchMapping("/{id}/image")
  public ResponseEntity<StatusDTO> updateImage(
      @PathVariable Long id, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = movieService.updateMovieImage(id, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}/image/{imageId}")
  public ResponseEntity<StatusDTO> deleteImage(
      @PathVariable Long id, @PathVariable String imageId) {
    StatusDTO statusDTO = movieService.deleteMovieImage(id, imageId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StatusDTO> update(
      @PathVariable Long id, @RequestBody @Valid MovieCreationDTO movie) {
    StatusDTO statusDTO = movieService.update(id, movie);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PatchMapping("/{id}/trailer")
  public ResponseEntity<StatusDTO> updateTrailer(
      @PathVariable Long id, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = movieService.updateMovieTrailer(id, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}/trailer")
  public ResponseEntity<StatusDTO> deleteTrailer(@PathVariable Long id) {
    StatusDTO statusDTO = movieService.deleteMovieTrailer(id);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{id}/actors/{actorId}")
  public ResponseEntity<StatusDTO> deleteActor(@PathVariable Long id, @PathVariable Long actorId) {
    StatusDTO statusDTO = movieService.deleteActor(id, actorId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
