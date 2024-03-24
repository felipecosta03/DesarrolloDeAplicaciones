package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.services.IUserFavoriteMovieService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-favorite-movies/user")
public class UserFavoriteMovieController {
    private final IUserFavoriteMovieService userFavoriteMovieService;

    public UserFavoriteMovieController(IUserFavoriteMovieService userFavoriteMovieService) {
        this.userFavoriteMovieService = userFavoriteMovieService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieDTO>> getFavoriteMovies(@PathVariable String userId) {
        return ResponseEntity.ok(userFavoriteMovieService.getFavoriteMovies(userId));
    }
    @PostMapping("/{userId}/movie/{movieId}")
    public ResponseEntity<List<MovieDTO>> getFavoriteMovies(@PathVariable String userId,@PathVariable String movieId) {
        return ResponseEntity.ok(userFavoriteMovieService.getFavoriteMovies(userId));
    }


}
