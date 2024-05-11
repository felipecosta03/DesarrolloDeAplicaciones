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
    if (!isNull(qualificationOrder)) {
      if (qualificationOrder.equals("asc")) {
        return "vote_average.asc";
      } else {
        return "vote_average.desc";
      }
    }
    if (!isNull(dateOrder) && dateOrder.equals("asc")) {
      return "release_date.asc";
    }
    return "release_date.desc";
  }
}
