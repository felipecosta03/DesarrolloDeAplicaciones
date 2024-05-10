package com.example.desarrollodeaplicaciones.repositories.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieDetailRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Qualifier("themoviedb")
public class ApiRetrieveMovieDetailRepository implements RetrieveMovieDetailRepository {

  private final WebClient webClient;

  public ApiRetrieveMovieDetailRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<MovieDetailDTO> apply(Model model) {
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
                HttpStatus.INTERNAL_SERVER_ERROR::equals,
                response -> response.bodyToMono(String.class).map(Exception::new))
            .bodyToMono(MovieDetailDTO.class)
            .block());
  }
}
