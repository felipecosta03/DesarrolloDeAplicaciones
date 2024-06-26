package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.SaveMovieDetailRepository;
import com.example.desarrollodeaplicaciones.usecases.SaveMovieDetail;
import com.example.desarrollodeaplicaciones.usecases.UploadImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultSaveMovieDetail implements SaveMovieDetail {

  private final SaveMovieDetailRepository saveMovieDetailRepository;
  private final UploadImage uploadImage;

  public DefaultSaveMovieDetail(
      SaveMovieDetailRepository saveMovieDetailRepository, UploadImage uploadImage) {
    this.saveMovieDetailRepository = saveMovieDetailRepository;
    this.uploadImage = uploadImage;
  }

  private void validateMovieDetail(MovieDetail movieDetail) {
    if (isNull(movieDetail)) {
      throw new BadRequestUseCaseException("MovieDetail is required");
    }

    if (isNull(movieDetail.getId())) {
      throw new BadRequestUseCaseException("MovieDetail id is required");
    }
  }

  @Override
  @Async
  public void accept(MovieDetail movieDetail) {
    validateMovieDetail(movieDetail);
    log.info("Saving movie detail with id: {}", movieDetail.getId());
    movieDetail.setPosterPath(
        uploadImage.apply(
            UploadImage.Model.builder().imageUrl(movieDetail.getPosterPath()).build()));
    movieDetail.setBackdropPath(
        uploadImage.apply(
            UploadImage.Model.builder().imageUrl(movieDetail.getBackdropPath()).build()));
    saveMovieDetailRepository.save(movieDetail);
    log.info("Movie detail with id: {} saved", movieDetail.getId());
  }
}
