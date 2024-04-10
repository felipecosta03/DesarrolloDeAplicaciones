package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import java.util.List;

public interface IUserFavoriteMovieService {
  StatusDTO addFavoriteMovie(Long userId, Long movieId);

  StatusDTO removeFavoriteMovie(Long userId, Long movieId);

  List<MovieSimpleDTO> getFavoriteMovies(Long userId);
}
