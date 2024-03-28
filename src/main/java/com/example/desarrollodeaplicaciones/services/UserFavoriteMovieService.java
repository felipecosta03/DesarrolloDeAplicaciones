package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteMovieService implements IUserFavoriteMovieService {
  private final IUserRepository userRepository;
  private final IMovieRepository movieRepository;

  public UserFavoriteMovieService(
      IUserRepository userRepository, IMovieRepository movieRepository) {
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
  }

  public StatusDTO addFavoriteMovie(Long userId, Long movieId) {
    User user = getUser(userId);
    Movie favoriteMovie =
        movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
    if (!user.getFavoriteMovies().contains(favoriteMovie)) {
      user.getFavoriteMovies().add(favoriteMovie);
      userRepository.save(user);
      return StatusDTO.builder().status(200).build();
    }
    return StatusDTO.builder().status(400).build();
  }

  public StatusDTO removeFavoriteMovie(Long userId, Long movieId) {
    User user = getUser(userId);
    boolean isMovieDeleted =
        user.getFavoriteMovies().removeIf(movie -> movie.getId().equals(movieId));
    if (isMovieDeleted) {
      userRepository.save(user);
      return StatusDTO.builder().status(200).build();
    }
    return StatusDTO.builder().status(400).build();
  }

  public List<MovieDTO> getFavoriteMovies(Long userId) {
    return getUser(userId).getFavoriteMovies().stream().map(Mapper::movieToMovieDTO).toList();
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }
}
