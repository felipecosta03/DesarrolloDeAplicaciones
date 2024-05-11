package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByGenreDatabaseRepository;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDTO;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByGenreDatabase implements RetrieveMoviesByGenreDatabase {

  private final RetrieveMoviesByGenreDatabaseRepository retrieveMoviesByGenreDatabaseRepository;
  private final BuildMovieDTO buildMovieDTO;

  public DefaultRetrieveMoviesByGenreDatabase(
      RetrieveMoviesByGenreDatabaseRepository retrieveMoviesByGenreDatabaseRepository,
      BuildMovieDTO buildMovieDTO) {
    this.retrieveMoviesByGenreDatabaseRepository = retrieveMoviesByGenreDatabaseRepository;
    this.buildMovieDTO = buildMovieDTO;
  }

  @Override
  public Optional<List<MovieSimpleDTO>> apply(Model model) {
    validateModel(model);
    Pageable pageable = PageRequest.of(model.getPage(), model.getSize(), getSort(model));
    return retrieveMoviesByGenreDatabaseRepository
        .findByGenreIdsContaining(model.getGenreId(), pageable)
        .map(movieSimples -> movieSimples.stream().map(buildMovieDTO).toList());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getGenreId())) {
      throw new BadRequestUseCaseException("Genre id is required");
    }
  }

  private Sort getSort(Model model) {
    String dateOrder = model.getDateOrder();
    String qualificationOrder = model.getQualificationOrder();
    Sort.Order sortByDate = null;
    if ("asc".equals(dateOrder)) {
      sortByDate = Sort.Order.asc("releaseDate");
    } else if ("desc".equals(dateOrder)) {
      sortByDate = Sort.Order.desc("releaseDate");
    }

    Sort.Order sortByQualification = null;
    if ("asc".equals(qualificationOrder)) {
      sortByQualification = Sort.Order.asc("voteAverage");
    } else if ("desc".equals(qualificationOrder)) {
      sortByQualification = Sort.Order.desc("voteAverage");
    }

    if (isNull(sortByDate) && isNull(sortByQualification)) {
      return Sort.by(Sort.Order.desc("releaseDate"));
    } else if (isNull(sortByDate)) {
      return Sort.by(sortByQualification);
    } else if (isNull(sortByQualification)) {
      return Sort.by(sortByDate);
    } else {
      return Sort.by(sortByDate, sortByQualification);
    }
  }
}
