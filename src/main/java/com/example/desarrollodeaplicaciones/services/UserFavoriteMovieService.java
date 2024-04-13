package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.FavoriteMovieAlreadyExistsException;
import com.example.desarrollodeaplicaciones.exceptions.FavoriteMovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteMovieService implements IUserFavoriteMovieService {
  private final IUserRepository userRepository;
  private final IMovieService movieService;

  public UserFavoriteMovieService(IUserRepository userRepository, IMovieService movieService) {
    this.userRepository = userRepository;
    this.movieService = movieService;
  }

  public StatusDTO addFavoriteMovie(Long userId, Long movieId) {
    User user = getUser(userId);
    if (user.getFavoriteMovies().contains(movieId)) {
      throw new FavoriteMovieAlreadyExistsException(movieId, userId);
    }
    if (movieService.existsMovie(movieId)) {
      user.getFavoriteMovies().add(movieId);
      userRepository.save(user);
      return StatusDTO.builder().status(200).build();
    }
    throw new MovieNotFoundException(movieId);
  }

  public StatusDTO removeFavoriteMovie(Long userId, Long movieId) {
    User user = getUser(userId);
    boolean isMovieDeleted = user.getFavoriteMovies().removeIf(movie -> movie.equals(movieId));
    if (!isMovieDeleted) {
      throw new FavoriteMovieNotFoundException(movieId, userId);
    }
    userRepository.save(user);
    return StatusDTO.builder().status(200).build();
  }

  public List<MovieSimpleDTO> getFavoriteMovies(Long userId) {
    return getUser(userId).getFavoriteMovies().stream()
        .map(movieService::getMovieSimpleById)
        .toList();
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }
}
