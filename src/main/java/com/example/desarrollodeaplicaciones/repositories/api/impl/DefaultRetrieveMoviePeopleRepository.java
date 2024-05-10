package com.example.desarrollodeaplicaciones.repositories.api.impl;

import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieCreditsApi;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMovieDetailApiRepository;
import com.example.desarrollodeaplicaciones.repositories.api.RetrieveMoviePeopleRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMoviePeopleRepository implements RetrieveMoviePeopleRepository {

  private final WebClient webClient;

  public DefaultRetrieveMoviePeopleRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<ResponseMovieCreditsApi> apply(RetrieveMovieDetailApiRepository.Model model) {
    return Optional.ofNullable(
        webClient
            .get() // TODO Crear excepciones personalizadas
            .uri(
                uriBuilder ->
                    uriBuilder.path(String.format("/movie/%s/credits", model.getMovieId())).build())
            .retrieve()
            .bodyToMono(ResponseMovieCreditsApi.class)
            .block());
  }
}
