package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.usecases.BuildRetrieveMoviesApiSort;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildRetrieveMoviesApiSort implements BuildRetrieveMoviesApiSort {
  @Override
  public String apply(Model model) {
    final String qualificationOrder = model.getQualificationOrder();
    final String dateOrder = model.getDateOrder();
    if (!isNull(dateOrder)) {
      if (dateOrder.equals("asc")) {
        return "release_date.asc";
      } else {
        return "release_date.desc";
      }
    }
    if (!isNull(qualificationOrder)) {
      if (qualificationOrder.equals("asc")) {
        return "vote_average.asc";
      } else if (qualificationOrder.equals("desc")) {
        return "vote_average.desc";
      }
    }
    return "release_date.desc";
  }
}
