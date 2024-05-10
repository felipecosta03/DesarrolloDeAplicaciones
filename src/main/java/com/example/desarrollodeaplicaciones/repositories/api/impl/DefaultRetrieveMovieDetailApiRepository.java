package com.example.desarrollodeaplicaciones.repositories.api.impl;

import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieDetailApiRepository;
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
