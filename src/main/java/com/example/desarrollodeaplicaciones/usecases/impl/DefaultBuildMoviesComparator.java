package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesComparator;
import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMoviesComparator implements BuildMoviesComparator {
  @Override
  public Comparator<MovieSimpleDto> apply(Model model) {
    validateModel(model);
    String dateOrder = model.getDateOrder();
    String qualificationOrder = model.getQualificationOrder();

    Comparator<MovieSimpleDto> dateComparator = null;

    if ("asc".equals(dateOrder)) {
      dateComparator = Comparator.comparing(MovieSimpleDto::getReleaseDate);
    } else if ("desc".equals(dateOrder)) {
      dateComparator = Comparator.comparing(MovieSimpleDto::getReleaseDate).reversed();
    }
    Comparator<MovieSimpleDto> qualificationComparator = null;
    if ("asc".equals(qualificationOrder)) {
      qualificationComparator = Comparator.comparing(MovieSimpleDto::getVoteAverage);
    } else if ("desc".equals(qualificationOrder)) {
      qualificationComparator = Comparator.comparing(MovieSimpleDto::getVoteAverage).reversed();
    }
    if (isNull(dateComparator) && isNull(qualificationComparator)) {
      return Comparator.comparing(MovieSimpleDto::getReleaseDate).reversed();
    } else if (isNull(dateComparator)) {
      return qualificationComparator;
    } else if (isNull(qualificationComparator)) {
      return dateComparator;
    } else {
      return dateComparator.thenComparing(qualificationComparator);
    }
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
  }
}
