package com.example.desarrollodeaplicaciones.repositories.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailApiRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMovieDetailApiRepository implements RetrieveMovieDetailApiRepository {

  private final WebClient webClient;

  public DefaultRetrieveMovieDetailApiRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<MovieDetailDTO> apply(Model model) {
    try {
      return Optional.ofNullable(
          webClient
              .get()
              .uri(
                  uriBuilder ->
                      uriBuilder
                          .path(String.format("/movie/%s", model.getMovieId()))
                          .queryParam("language", "es-es")
                          .build())
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
              .bodyToMono(MovieDetailDTO.class)
              .block());
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
