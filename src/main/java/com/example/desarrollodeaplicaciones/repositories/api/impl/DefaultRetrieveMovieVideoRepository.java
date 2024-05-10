package com.example.desarrollodeaplicaciones.repositories.api.impl;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideoApi;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieVideoRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMovieVideoRepository implements RetrieveMovieVideoRepository {

  private final WebClient webClient;

  public DefaultRetrieveMovieVideoRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<ResponseMovieVideoApi> apply(RetrieveMovieDetailApiRepository.Model model) {
    return Optional.ofNullable(
        webClient
            .get() // TODO Crear excepciones personalizadas
            .uri(
                uriBuilder ->
                    uriBuilder.path(String.format("/movie/%s/videos", model.getMovieId())).build())
            .retrieve()
            .bodyToMono(ResponseMovieVideoApi.class)
            .block());
  }
}
