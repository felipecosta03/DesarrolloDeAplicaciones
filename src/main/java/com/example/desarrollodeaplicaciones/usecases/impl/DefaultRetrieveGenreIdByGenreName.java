package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.repositories.RetrieveGenresApiRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveGenresRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveGenreIdByGenreName;
import com.example.desarrollodeaplicaciones.usecases.SaveGenresDto;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveGenreIdByGenreName implements RetrieveGenreIdByGenreName {

  private final RetrieveGenresRepository retrieveGenresRepository;
  private final RetrieveGenresApiRepository retrieveGenresApiRepository;
  private final SaveGenresDto saveGenresDto;

  public DefaultRetrieveGenreIdByGenreName(
      RetrieveGenresRepository retrieveGenresRepository,
      RetrieveGenresApiRepository retrieveGenresApiRepository,
      SaveGenresDto saveGenresDto) {
    this.retrieveGenresRepository = retrieveGenresRepository;
    this.retrieveGenresApiRepository = retrieveGenresApiRepository;
    this.saveGenresDto = saveGenresDto;
  }

  @Override
  public Optional<Integer> apply(String genreName) {
    final Optional<List<Genre>> genres = retrieveGenresRepository.findAll();
    if (genres.isPresent()) {
      Optional<Genre> genre =
          genres.get().stream().filter(g -> g.getName().equals(genreName)).findFirst();
      if (genre.isPresent()) {
        return Optional.of(genre.get().getId());
      }
    }

    final Optional<List<GenreDto>> genresDto = retrieveGenresApiRepository.get();

    if (genresDto.isPresent()) {
      saveGenresDto.accept(genresDto.get());
      Optional<GenreDto> genreDto =
          genresDto.get().stream().filter(g -> g.getName().equals(genreName)).findFirst();
      return genreDto.map(GenreDto::getId);
    }
    return Optional.empty();
  }
}
