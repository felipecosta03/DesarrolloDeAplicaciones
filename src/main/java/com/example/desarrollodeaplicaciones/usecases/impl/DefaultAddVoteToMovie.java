package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.Vote;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.repositories.SaveMovieDetailRepository;
import com.example.desarrollodeaplicaciones.usecases.AddVoteToMovie;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMovieDetailDatabase;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultAddVoteToMovie implements AddVoteToMovie {

  private final RetrieveMovieDetailDatabase retrieveMovieDetailDatabase;
  private final SaveMovieDetailRepository saveMovieDetailRepository;

  public DefaultAddVoteToMovie(
      RetrieveMovieDetailDatabase retrieveMovieDetailDatabase,
      SaveMovieDetailRepository saveMovieDetailRepository) {
    this.retrieveMovieDetailDatabase = retrieveMovieDetailDatabase;
    this.saveMovieDetailRepository = saveMovieDetailRepository;
  }

  @Override
  public void accept(Model model) {
    validateModel(model);
    Optional<MovieDetail> movieDetail =
        retrieveMovieDetailDatabase.apply(
            RetrieveMovieDetailDatabase.Model.builder().movieId(model.getMovieId()).build());

    if (movieDetail.isEmpty()) {
      throw new NotFoundUseCaseException("Movie not found");
    }

    Optional<Vote> vote =
        movieDetail.get().getVotes().stream()
            .filter(v -> v.getUserId().equals(model.getUserId()))
            .findFirst();

    if (vote.isPresent()) {
      vote.get().setScore(model.getScore());
    } else {
      movieDetail
          .get()
          .getVotes()
          .add(Vote.builder().userId(model.getUserId()).score(model.getScore()).build());
    }

    saveMovieDetailRepository.save(movieDetail.get());
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }

    if (isNull(model.getUserId())) {
      throw new BadRequestUseCaseException("UserId is required");
    }

    if (isNull(model.getMovieId())) {
      throw new BadRequestUseCaseException("MovieId is required");
    }

    if (isNull(model.getScore())) {
      throw new BadRequestUseCaseException("Score is required");
    }
  }
}
