package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveGenreIdByGenreName;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenre;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreApi;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByGenreDatabase;
import com.example.desarrollodeaplicaciones.usecases.SaveMoviesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesByGenre implements RetrieveMoviesByGenre {

  private final RetrieveGenreIdByGenreName retrieveGenreIdByGenreName;
  private final RetrieveMoviesByGenreDatabase retrieveMoviesByGenreDatabase;
  private final RetrieveMoviesByGenreApi retrieveMoviesByGenreApi;
  private final SaveMoviesDto saveMoviesDTO;

  public DefaultRetrieveMoviesByGenre(
      RetrieveGenreIdByGenreName retrieveGenreIdByGenreName,
      RetrieveMoviesByGenreDatabase retrieveMoviesByGenreDatabase,
      RetrieveMoviesByGenreApi retrieveMoviesByGenreApi,
      SaveMoviesDto saveMoviesDTO) {
    this.retrieveGenreIdByGenreName = retrieveGenreIdByGenreName;
    this.retrieveMoviesByGenreDatabase = retrieveMoviesByGenreDatabase;
    this.retrieveMoviesByGenreApi = retrieveMoviesByGenreApi;
    this.saveMoviesDTO = saveMoviesDTO;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);

    Optional<Integer> genreId = retrieveGenreIdByGenreName.apply(model.getGenre());

    if (genreId.isPresent()) {
      // get from api
      Optional<List<MovieSimpleDto>> moviesDTO =
          retrieveMoviesByGenreApi.apply(
              RetrieveMoviesByGenreApi.Model.builder()
                  .dateOrder(model.getDateOrder())
                  .qualificationOrder(model.getQualificationOrder())
                  .genreId(genreId.get())
                  .page(model.getPage())
                  .size(model.getSize())
                  .build());

      if (moviesDTO.isPresent()) {
        saveMoviesDTO.accept(moviesDTO.get());
        return moviesDTO;
      }

      // Get from database
      return retrieveMoviesByGenreDatabase.apply(
          RetrieveMoviesByGenreDatabase.Model.builder()
              .dateOrder(model.getDateOrder())
              .qualificationOrder(model.getQualificationOrder())
              .genreId(genreId.get())
              .page(model.getPage())
              .size(model.getSize())
              .build());
    }

    throw new NotFoundUseCaseException("Genre not found");
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (isNull(model.getGenre()) || model.getGenre().isBlank()) {
      throw new BadRequestUseCaseException("Genre is required");
    }
  }
}
