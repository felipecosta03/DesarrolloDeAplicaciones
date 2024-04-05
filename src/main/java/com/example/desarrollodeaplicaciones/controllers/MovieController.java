package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
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
@Tag(name = "Movies")
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final IMovieService movieService;

  public MovieController(IMovieService movieController) {
    this.movieService = movieController;
  }

  @GetMapping
  public ResponseEntity<List<MovieDTO>> findAll(
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
  public ResponseEntity<MovieDTO> findById(@PathVariable Long movieId) {
    return ResponseEntity.status(200).body(movieService.findById(movieId));
  }

  @PatchMapping("/{movieId}/image")
  public ResponseEntity<StatusDTO> updateImage(
          @PathVariable Long movieId, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = movieService.addMovieImage(movieId, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{movieId}/image/{imageId}")
  public ResponseEntity<StatusDTO> deleteImage(
          @PathVariable Long movieId, @PathVariable String imageId) {
    StatusDTO statusDTO = movieService.deleteMovieImage(movieId, imageId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PatchMapping("/{movieId}/trailer")
  public ResponseEntity<StatusDTO> updateTrailer(
          @PathVariable Long movieId, @RequestParam("image") MultipartFile image) {
    StatusDTO statusDTO = movieService.updateMovieTrailer(movieId, image);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{movieId}/trailer")
  public ResponseEntity<StatusDTO> deleteTrailer(@PathVariable Long movieId) {
    StatusDTO statusDTO = movieService.deleteMovieTrailer(movieId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{movieId}/actors/{actorId}")
  public ResponseEntity<StatusDTO> deleteActor(@PathVariable Long movieId, @PathVariable Long actorId) {
    StatusDTO statusDTO = movieService.deleteActor(movieId, actorId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @PostMapping("/{movieId}/rate")
  public ResponseEntity<StatusDTO> addRate(
          @PathVariable Long movieId,@Valid @RequestBody RateDTO rate) {
    StatusDTO statusDTO = movieService.addRate(movieId, rate);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
  @PutMapping("/{movieId}/rate")
  public ResponseEntity<StatusDTO> updateRate(
          @PathVariable Long movieId,@Valid @RequestBody RateDTO rate) {
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
