package com.example.desarrollodeaplicaciones.services;

import java.util.List;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;

public interface IMovieService {

  public MovieDTO add(MovieDTO movie);

  public List<MovieDTO> getAll();

  public MovieDTO findById(Long id);

  StatusDTO update(Long id, MovieDTO movie);
}
