package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.ErrorMessageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.services.IUserFavoriteMovieService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-favorite-movies/user")
@Tag(name = "User Favorite Movies")
public class UserFavoriteMovieController {
  private final IUserFavoriteMovieService userFavoriteMovieService;

  public UserFavoriteMovieController(IUserFavoriteMovieService userFavoriteMovieService) {
    this.userFavoriteMovieService = userFavoriteMovieService;
  }

  @GetMapping("/{userId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Favorites movies found"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<List<MovieSimpleDTO>> getFavoriteMovies(@PathVariable Long userId) {
    return ResponseEntity.ok(userFavoriteMovieService.getFavoriteMovies(userId));
  }

  @PostMapping("/{userId}/movie/{movieId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Favorites movies found"),
        @ApiResponse(
            responseCode = "400",
            description = "Movie already added as favorite",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> addFavoriteMovie(
      @PathVariable Long userId, @PathVariable Long movieId) {
    StatusDTO statusDTO = userFavoriteMovieService.addFavoriteMovie(userId, movieId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }

  @DeleteMapping("/{userId}/movie/{movieId}")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Favorites movies found"),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Database error",
            content = {@Content(schema = @Schema(implementation = ErrorMessageDTO.class))})
      })
  public ResponseEntity<StatusDTO> deleteFavoriteMovie(
      @PathVariable Long userId, @PathVariable Long movieId) {
    StatusDTO statusDTO = userFavoriteMovieService.removeFavoriteMovie(userId, movieId);
    return ResponseEntity.status(statusDTO.getStatus()).body(statusDTO);
  }
}
