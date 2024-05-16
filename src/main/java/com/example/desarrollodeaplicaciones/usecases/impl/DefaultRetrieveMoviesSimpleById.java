package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesSimpleByIdRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieSimpleDtoByMovieDetailDto;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesSimpleById;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesSimpleById implements RetrieveMoviesSimpleById {

  private final BuildMoviesDto buildMoviesDto;
  private final RetrieveMoviesSimpleByIdRepository retrieveMoviesSimpleByIdRepository;
  private final RetrieveMovieDetailApiRepository retrieveMovieDetailApiRepository;
  private final BuildMovieSimpleDtoByMovieDetailDto buildMovieSimpleDtoByMovieDetailDto;
  private final SaveMoviesDto saveMoviesDto;

  public DefaultRetrieveMoviesSimpleById(
      BuildMoviesDto buildMoviesDto,
      RetrieveMoviesSimpleByIdRepository retrieveMoviesSimpleByIdRepository,
      RetrieveMovieDetailApiRepository retrieveMovieDetailApiRepository,
      BuildMovieSimpleDtoByMovieDetailDto buildMovieSimpleDtoByMovieDetailDto,
      SaveMoviesDto saveMoviesDto) {
    this.buildMoviesDto = buildMoviesDto;
    this.retrieveMoviesSimpleByIdRepository = retrieveMoviesSimpleByIdRepository;
    this.retrieveMovieDetailApiRepository = retrieveMovieDetailApiRepository;
    this.buildMovieSimpleDtoByMovieDetailDto = buildMovieSimpleDtoByMovieDetailDto;
    this.saveMoviesDto = saveMoviesDto;
  }

  @Override
  public List<MovieSimpleDto> apply(Model model) {
    validateModel(model);
    Optional<List<MovieSimple>> moviesDatabase =
        retrieveMoviesSimpleByIdRepository.findAllById(model.getMoviesId());
    if (moviesDatabase.isEmpty() || moviesDatabase.get().isEmpty()) {
      throw new NotFoundUseCaseException("Movies not found");
    }
    if (moviesDatabase.get().size() == model.getMoviesId().size()) {
      return buildMoviesDto.apply(moviesDatabase.get());
    }
    final List<Long> notFoundMoviesId =
        model.getMoviesId().stream()
            .filter(
                id -> moviesDatabase.get().stream().noneMatch(movie -> movie.getId().equals(id)))
            .toList();
    final List<MovieDetailDto> moviesDetail = new ArrayList<>();
    for (Long id : notFoundMoviesId) {
      moviesDetail.add(
          retrieveMovieDetailApiRepository
              .apply(RetrieveMovieDetailApiRepository.Model.builder().movieId(id).build())
              .orElseThrow(() -> new NotFoundUseCaseException("Movie not found")));
    }
    final List<MovieSimpleDto> moviesSimple =
        moviesDetail.stream().map(buildMovieSimpleDtoByMovieDetailDto).collect(Collectors.toList());
    moviesSimple.addAll(buildMoviesDto.apply(moviesDatabase.get()));
    saveMoviesDto.accept(moviesSimple);
    return moviesSimple;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getMoviesId())) {
      throw new BadRequestUseCaseException("Model moviesId is required");
    }
  }
}
