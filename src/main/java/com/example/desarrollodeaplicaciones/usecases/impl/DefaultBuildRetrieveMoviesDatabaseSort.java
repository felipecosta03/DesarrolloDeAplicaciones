package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesDatabaseSort;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildRetrieveMoviesDatabaseSort implements BuildRetrieveMoviesDatabaseSort {
  @Override
  public Sort apply(Model model) {
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
