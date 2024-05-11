package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.usecases.SaveGenres;
import com.example.desarrollodeaplicaciones.usecases.SaveGenresDto;
import java.util.List;
import java.util.Objects;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveGenresDto implements SaveGenresDto {

  private final SaveGenres saveGenres;

  public DefaultSaveGenresDto(SaveGenres saveGenres) {
    this.saveGenres = saveGenres;
  }

  @Override
  @Async
  public void accept(List<GenreDto> genreDtos) {
    validateGenres(genreDtos);
    List<Genre> genres = genreDtos.stream().map(this::buildGenre).toList();
    saveGenres.save(genres);
  }

  private void validateGenres(List<GenreDto> genreDtos) {
    if (isNull(genreDtos) || genreDtos.isEmpty()) {
      throw new BadRequestUseCaseException("Genres are required");
    }

    if (genreDtos.stream().anyMatch(Objects::isNull)) {
      throw new BadRequestUseCaseException("Genre is required");
    }

    if (genreDtos.stream().anyMatch(g -> isNull(g.getId()) || g.getName().isBlank())) {
      throw new BadRequestUseCaseException("Genre id and name are required");
    }
  }

  private Genre buildGenre(GenreDto genreDto) {
    return Genre.builder().id(genreDto.getId()).name(genreDto.getName()).build();
  }
}
