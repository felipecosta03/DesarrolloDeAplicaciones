package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByTitleApiRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesComparator;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitleApi;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByTitleApi implements RetrieveMoviesByTitleApi {

  private final RetrieveMoviesByTitleApiRepository retrieveMoviesByTitleApiRepository;
  private final BuildMoviesComparator buildMoviesComparator;

  public DefaultRetrieveMoviesByTitleApi(
      RetrieveMoviesByTitleApiRepository retrieveMoviesByTitleApiRepository,
      BuildMoviesComparator buildMoviesComparator) {
    this.retrieveMoviesByTitleApiRepository = retrieveMoviesByTitleApiRepository;
    this.buildMoviesComparator = buildMoviesComparator;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    final List<MovieSimpleDto> movies = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      retrieveMoviesByTitleApiRepository
          .apply(
              RetrieveMoviesByTitleApiRepository.Model.builder()
                  .title(model.getTitle())
                  .page((model.getPage() / 10) * 10 + i)
                  .size(model.getSize())
                  .build())
          .ifPresent(movies::addAll);
    }
    movies.stream()
        .filter(movie -> isNull(movie.getReleaseDate()))
        .toList()
        .forEach(movie -> movie.setReleaseDate(""));
    Comparator<MovieSimpleDto> comparator =
        buildMoviesComparator.apply(
            BuildMoviesComparator.Model.builder()
                .dateOrder(model.getDateOrder())
                .qualificationOrder(model.getQualificationOrder())
                .build());
    movies.sort(comparator);
    int initialIndex = model.getPage() * model.getSize();
    if (initialIndex >= movies.size()) {
      return Optional.empty();
    }
    int finalIndex = Math.min(initialIndex + model.getSize(), movies.size());
    return Optional.of(movies.subList(initialIndex, finalIndex));
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getTitle().isEmpty()) {
      throw new BadRequestUseCaseException("Title is required");
    }
  }
}
