package com.example.desarrollodeaplicaciones.repositories.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseActorsSearchApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveActorsByNameApiRepository;
import com.example.desarrollodeaplicaciones.usecases.FixMovie;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveActorsByNameApiRepository implements RetrieveActorsByNameApiRepository {
  private final WebClient webClient;
  private final FixMovie fixMovie;

  public DefaultRetrieveActorsByNameApiRepository(WebClient webClient, FixMovie fixMovie) {
    this.webClient = webClient;
    this.fixMovie = fixMovie;
  }

  @Override
  public Optional<List<ActorDto>> apply(Model model) {
    validateModel(model);
    try {
      return Optional.ofNullable(
              webClient
                  .get()
                  .uri(
                      uriBuilder ->
                          uriBuilder
                              .path("/search/person")
                              .queryParam("language", "es-es")
                              .queryParam("page", model.getPage())
                              .queryParam("primary_release_date.lte", LocalDate.now())
                              .queryParam("query", model.getActorName())
                              .build())
                  .retrieve()
                  .onStatus(
                      HttpStatus.INTERNAL_SERVER_ERROR::equals,
                      response -> response.bodyToMono(String.class).map(Exception::new))
                  .onStatus(
                      HttpStatus.BAD_REQUEST::equals,
                      response -> response.bodyToMono(String.class).map(Exception::new))
                  .bodyToMono(ResponseActorsSearchApi.class)
                  .block())
          .map(ResponseActorsSearchApi::getResults)
          .map(
              actors -> {
                actors.forEach(actorDto -> actorDto.getKnownFor().forEach(fixMovie));
                return actors;
              });
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestRepositoryException("Model is required");
    }

    if (model.getActorName().isEmpty()) {
      throw new BadRequestRepositoryException("Actor name is required");
    }
  }
}
