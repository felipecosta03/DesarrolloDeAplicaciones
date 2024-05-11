package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.usecases.SaveGenres;
import com.example.desarrollodeaplicaciones.usecases.SaveGenresDTO;
import java.util.List;
import java.util.Objects;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveGenresDTO implements SaveGenresDTO {

  private final SaveGenres saveGenres;

  public DefaultSaveGenresDTO(SaveGenres saveGenres) {
    this.saveGenres = saveGenres;
  }

  @Override
  @Async
  public void accept(List<GenreDTO> genreDTOS) {
    validateGenres(genreDTOS);
    List<Genre> genres = genreDTOS.stream().map(this::buildGenre).toList();
    saveGenres.save(genres);
  }

  private void validateGenres(List<GenreDTO> genreDTOS) {
    if (isNull(genreDTOS) || genreDTOS.isEmpty()) {
      throw new BadRequestUseCaseException("Genres are required");
    }

    if (genreDTOS.stream().anyMatch(Objects::isNull)) {
      throw new BadRequestUseCaseException("Genre is required");
    }

    if (genreDTOS.stream().anyMatch(g -> isNull(g.getId()) || g.getName().isBlank())) {
      throw new BadRequestUseCaseException("Genre id and name are required");
    }
  }

  private Genre buildGenre(GenreDTO genreDTO) {
    return Genre.builder().id(genreDTO.getId()).name(genreDTO.getName()).build();
  }
}
