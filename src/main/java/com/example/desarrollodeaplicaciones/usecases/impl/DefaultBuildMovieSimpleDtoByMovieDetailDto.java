package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieSimpleDtoByMovieDetailDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieSimpleDtoByMovieDetailDto
    implements BuildMovieSimpleDtoByMovieDetailDto {
  @Override
  public MovieSimpleDto apply(MovieDetailDto movieDetailDto) {
    validateMovie(movieDetailDto);
    return MovieSimpleDto.builder()
        .id(movieDetailDto.getId())
        .title(movieDetailDto.getTitle())
        .genreIds(buildGenresId(movieDetailDto.getGenres()))
        .posterPath(movieDetailDto.getPosterPath())
        .voteAverage(movieDetailDto.getVoteAverage())
        .releaseDate(movieDetailDto.getReleaseDate())
        .popularity(movieDetailDto.getPopularity())
        .backdropPath(movieDetailDto.getBackdropPath())
        .build();
  }

  private List<Integer> buildGenresId(List<GenreDto> genreDtos) {
    return genreDtos.stream().map(GenreDto::getId).toList();
  }

  private void validateMovie(MovieDetailDto movieDetailDto) {
    if (isNull(movieDetailDto)) {
      throw new BadRequestUseCaseException("MovieDetailDto is required");
    }
    if (isNull(movieDetailDto.getId())) {
      throw new BadRequestUseCaseException("MovieDetailDto id is required");
    }
  }
}
