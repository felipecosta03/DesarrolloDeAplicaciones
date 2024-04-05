package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieSimpleApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieDetailApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.response.ResponseMovieImagesApiDTO;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import com.example.desarrollodeaplicaciones.services.MoviesApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PruebaController {

  private final MoviesApiService moviesApiService;
  private final MoviesApiRepositoryImpl moviesApiRepository;

  public PruebaController(
      MoviesApiService moviesApiService, MoviesApiRepositoryImpl moviesApiRepository) {
    this.moviesApiService = moviesApiService;
    this.moviesApiRepository = moviesApiRepository;
  }

  @GetMapping("/discover/movies")
  public ResponseEntity<List<MovieSimpleApiDTO>> discoverMovies(
      @RequestParam(defaultValue = "1") Integer page) {
    return ResponseEntity.ok(moviesApiService.getMoviesByPage(page));
  }

  @GetMapping("/{movieId}")
  public ResponseEntity<MovieDetailApiDTO> getmovie(@PathVariable Integer movieId) {
    return ResponseEntity.ok(moviesApiService.getMovieDetailById(movieId));
  }

  @GetMapping("/{movieId}/images")
  public ResponseEntity<ResponseMovieImagesApiDTO> getmovieimages(@PathVariable Integer movieId) {
    return ResponseEntity.ok(moviesApiRepository.getMovieImages(movieId));
  }

  @GetMapping("/{movieId}/videos")
  public ResponseEntity<?> getmovievideos(@PathVariable Integer movieId) {
    return ResponseEntity.ok(moviesApiRepository.getMovieVideos(movieId));
  }
}
