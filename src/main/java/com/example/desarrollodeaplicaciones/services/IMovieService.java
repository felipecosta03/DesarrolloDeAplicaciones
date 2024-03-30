package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IMovieService {

  StatusDTO add(MovieCreationDTO movie);

  List<MovieDTO> getAll();

  MovieDTO findById(Long id);

  StatusDTO updateMovieImage(Long id, MultipartFile image);

  StatusDTO deleteMovieImage(Long id, String mediaId);

  StatusDTO updateMovieTrailer(Long id, MultipartFile image);

  StatusDTO deleteMovieTrailer(Long id);

  StatusDTO update(Long id, MovieCreationDTO movie);

  StatusDTO deleteActor(Long id, Long actorId);
}
