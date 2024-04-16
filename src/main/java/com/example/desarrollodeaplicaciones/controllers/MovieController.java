package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.services.IMovieService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Movies found"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<List<MovieSimpleDTO>> findAll(
      @RequestParam(required = false) Optional<String> dateOrder,
      @RequestParam(required = false) Optional<String> qualificationOrder,
      @RequestParam(required = false) Optional<String> genre,
      @RequestParam(required = false) Optional<Integer> page) {
    return ResponseEntity.status(200)
        .body(movieService.getAll(dateOrder, qualificationOrder, genre, page));
  }

  @GetMapping("/search")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Movies found"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<List<MovieSimpleDTO>> findAllByTitleOrActor(
      @RequestParam(required = false) Optional<String> dateOrder,
      @RequestParam(required = false) Optional<String> qualificationOrder,
      @RequestParam(required = false) Optional<String> value,
      @RequestParam(required = false) Optional<Integer> page) {
    return ResponseEntity.status(200)
        .body(movieService.getAllByTitleOrActor(dateOrder, qualificationOrder, value, page));
  }

  @GetMapping("/{movieId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Movies found"),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<MovieDetailDTO> findById(@PathVariable Long movieId) {
    return ResponseEntity.status(200).body(movieService.findById(movieId));
  }

  @PutMapping("/{movieId}/rate")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Rate updated"),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> updateRate(
      @PathVariable Long movieId, @Valid @RequestBody RateDTO rate) {
    StatusDTO statusDTO = movieService.updateRate(movieId, rate);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{movieId}/rate/{userId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Rate deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "Rate not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> deleteRate(
      @PathVariable Long movieId, @PathVariable Long userId) {
    StatusDTO statusDTO = movieService.deleteRate(movieId, userId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
