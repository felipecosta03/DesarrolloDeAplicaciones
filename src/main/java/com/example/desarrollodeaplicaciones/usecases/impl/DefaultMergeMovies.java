package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.usecases.MergeMovies;
import com.example.desarrollodeaplicaciones.usecases.RemoveDuplicatedMovies;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultMergeMovies implements MergeMovies {

  private final RemoveDuplicatedMovies removeDuplicatedMovies;

  public DefaultMergeMovies(RemoveDuplicatedMovies removeDuplicatedMovies) {
    this.removeDuplicatedMovies = removeDuplicatedMovies;
  }

  @Override
  public List<MovieSimpleDto> apply(List<MovieSimpleDto> movies1, List<MovieSimpleDto> movies2) {
    final List<MovieSimpleDto> mergedMovies = new ArrayList<>(movies1);
    mergedMovies.addAll(movies2);
    return removeDuplicatedMovies.apply(mergedMovies);
  }
}
