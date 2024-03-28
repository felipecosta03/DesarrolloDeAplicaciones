package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;

import java.util.List;

public interface IUserFavoriteMovieService {
    List<MovieDTO> addFavoriteMovie(Long userId, Long movieId);
    List<MovieDTO> removeFavoriteMovie(Long userId, Long movieId);
    List<MovieDTO> getFavoriteMovies(Long userId);
}
