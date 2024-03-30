package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface IMovieService {

  StatusDTO add(MovieCreationDTO movie);

  MovieDTO findById(Long id);

  StatusDTO updateMovieImage(Long id, MultipartFile image);

  StatusDTO deleteMovieImage(Long id, String mediaId);

  StatusDTO updateMovieTrailer(Long id, MultipartFile image);

  StatusDTO deleteMovieTrailer(Long id);

  StatusDTO update(Long id, MovieCreationDTO movie);

  StatusDTO deleteActor(Long id, Long actorId);

  List<MovieDTO> getAll(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> genre,
      Optional<Integer> page);

  List<MovieDTO> getAllByTitleOrActor(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> value,
      Optional<Integer> page);
}
