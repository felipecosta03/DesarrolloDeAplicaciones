package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;

import java.util.List;
import java.util.Optional;

public interface IMovieService {

  MovieDetailDTO findById(Long id);

  List<MovieSimpleDTO> getAll(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> genre,
      Optional<Integer> page);

  List<MovieSimpleDTO> getAllByTitleOrActor(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> value,
      Optional<Integer> page);

  StatusDTO addRate(Long movieId, RateDTO rate);

  StatusDTO updateRate(Long movieId, RateDTO rate);

  StatusDTO deleteRate(Long movieId, Long userId);

  MovieSimpleDTO getMovieSimpleById(Long movieId);

  boolean existsMovie(Long movieId);
}
