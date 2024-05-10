package com.example.desarrollodeaplicaciones.repositories.api.impl;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieImageRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMovieImageRepository implements RetrieveMovieImageRepository {

  private final WebClient webClient;

  public DefaultRetrieveMovieImageRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<ResponseMovieImagesApi> apply(RetrieveMovieDetailApiRepository.Model model) {
    return Optional.ofNullable(
        webClient
            .get() // TODO Crear excepciones personalizadas
            .uri(
                uriBuilder ->
                    uriBuilder.path(String.format("/movie/%s/images", model.getMovieId())).build())
            .retrieve()
            .bodyToMono(ResponseMovieImagesApi.class)
            .block());
  }
}
