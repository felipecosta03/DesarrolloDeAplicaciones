package com.example.desarrollodeaplicaciones.repositories.impl;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieGenreApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveGenresApiRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveGenresApiRepository implements RetrieveGenresApiRepository {

  private final WebClient webClient;

  public DefaultRetrieveGenresApiRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<List<GenreDTO>> get() {
    try {
      return Optional.ofNullable(
              webClient
                  .get()
                  .uri(
                      uriBuilder ->
                          uriBuilder.path("/genre/movie/list").queryParam("language", "es").build())
                  .retrieve()
                  .onStatus(
                      HttpStatus.NOT_FOUND::equals,
                      clientResponse -> {
                        throw new NotFoundRepositoryException("Movie not found");
                      })
                  .onStatus(
                      HttpStatus.BAD_REQUEST::equals,
                      clientResponse -> {
                        throw new BadRequestRepositoryException(clientResponse.logPrefix());
                      })
                  .onStatus(
                      HttpStatus.FAILED_DEPENDENCY::equals,
                      clientResponse -> {
                        throw new FailedDependencyRepositoryException(clientResponse.logPrefix());
                      })
                  .bodyToMono(ResponseMovieGenreApi.class)
                  .block())
          .map(ResponseMovieGenreApi::getGenres);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
