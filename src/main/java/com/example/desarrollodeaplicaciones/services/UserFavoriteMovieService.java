package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteMovieService implements IUserFavoriteMovieService {
  private final IUserRepository userRepository;
  private final IMovieRepository movieRepository;

  public UserFavoriteMovieService(
      IUserRepository userRepository, IMovieRepository movieRepository) {
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
  }

  @Override
  public List<MovieDTO> addFavoriteMovie(String userId, String movieId) {
    User user = getUser(userId);
    Movie favoriteMovie =
        movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
    if (!user.getFavoriteMovies().contains(favoriteMovie)) {
      user.getFavoriteMovies().add(favoriteMovie);
    }
    return user.getFavoriteMovies().stream().map(Mapper::movieToMovieDTO).toList();
  }

  @Override
  public List<MovieDTO> removeFavoriteMovie(String userId, String movieId) {
    User user = getUser(userId);
    user.getFavoriteMovies().removeIf(movie -> movie.getId().equals(movieId));
    return user.getFavoriteMovies().stream().map(Mapper::movieToMovieDTO).toList();
  }

  private User getUser(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @Override
  public List<MovieDTO> getFavoriteMovies(String userId) {
    return getUser(userId).getFavoriteMovies().stream().map(Mapper::movieToMovieDTO).toList();
  }
}
