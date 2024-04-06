package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.services.IMovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "Movies")
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final IMovieService movieService;

  public MovieController(IMovieService movieController) {
    this.movieService = movieController;
  }

  @GetMapping
  public ResponseEntity<List<MovieSimpleDTO>> findAll(
      @RequestParam(required = false) Optional<String> dateOrder,
      @RequestParam(required = false) Optional<String> qualificationOrder,
      @RequestParam(required = false) Optional<String> genre,
      @RequestParam(required = false) Optional<Integer> page) {
    return ResponseEntity.status(200)
        .body(movieService.getAll(dateOrder, qualificationOrder, genre, page));
  }

  @GetMapping("/search")
  public ResponseEntity<List<MovieDTO>> findAllByTitleOrActor(
      @RequestParam(required = false) Optional<String> dateOrder,
      @RequestParam(required = false) Optional<String> qualificationOrder,
      @RequestParam(required = false) Optional<String> value,
      @RequestParam(required = false) Optional<Integer> page) {
    return ResponseEntity.status(200)
        .body(movieService.getAllByTitleOrActor(dateOrder, qualificationOrder, value, page));
  }

  @GetMapping("/{movieId}")
  public ResponseEntity<MovieDetailDTO> findById(@PathVariable Long movieId) {
    return ResponseEntity.status(200).body(movieService.findById(movieId));
  }

  @PostMapping("/{movieId}/rate")
  public ResponseEntity<StatusDTO> addRate(
      @PathVariable Long movieId, @Valid @RequestBody RateDTO rate) {
    StatusDTO statusDTO = movieService.addRate(movieId, rate);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PutMapping("/{movieId}/rate")
  public ResponseEntity<StatusDTO> updateRate(
      @PathVariable Long movieId, @Valid @RequestBody RateDTO rate) {
    StatusDTO statusDTO = movieService.updateRate(movieId, rate);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{movieId}/rate/{userId}")
  public ResponseEntity<StatusDTO> deleteRate(
      @PathVariable Long movieId, @PathVariable Long userId) {
    StatusDTO statusDTO = movieService.deleteRate(movieId, userId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
