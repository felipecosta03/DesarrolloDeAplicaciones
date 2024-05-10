package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.SaveMovieDetailRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveMovieDetail implements SaveMovieDetail {

  private final SaveMovieDetailRepository saveMovieDetailRepository;

  public DefaultSaveMovieDetail(SaveMovieDetailRepository saveMovieDetailRepository) {
    this.saveMovieDetailRepository = saveMovieDetailRepository;
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }

    if (isNull(model.getMovieDetail())) {
      throw new BadRequestUseCaseException("MovieDetail is required");
    }

    if (isNull(model.getMovieDetail().getId())) {
      throw new BadRequestUseCaseException("MovieDetail id is required");
    }
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    saveMovieDetailRepository.save(model.getMovieDetail());
  }
}
