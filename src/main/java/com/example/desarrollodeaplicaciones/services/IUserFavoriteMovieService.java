package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;

import java.util.List;

public interface IUserFavoriteMovieService {
    List<MovieDTO> addFavoriteMovie(String userId, String movieId);
    List<MovieDTO> removeFavoriteMovie(String userId, String movieId);
    List<MovieDTO> getFavoriteMovies(String userId);
}
