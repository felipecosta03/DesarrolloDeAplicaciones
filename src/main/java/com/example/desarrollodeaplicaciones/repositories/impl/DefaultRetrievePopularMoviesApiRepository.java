package com.example.desarrollodeaplicaciones.repositories.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseDiscoverMoviesApi;
import com.example.desarrollodeaplicaciones.repositories.RetrievePopularMoviesApiRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrievePopularMoviesApiRepository
    implements RetrievePopularMoviesApiRepository {

  private final WebClient webClient;

  public DefaultRetrievePopularMoviesApiRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    try {
      return Optional.ofNullable(
              webClient
                  .get()
                  .uri(
                      uriBuilder ->
                          uriBuilder
                              .path("/discover/movie")
                              .queryParam("language", "es-es")
                              .queryParam("page", model.getPage())
                              .queryParam("primary_release_date.lte", LocalDate.now())
                              .queryParam("sort_by", "popularity.desc")
                              .build())
                  .retrieve()
                  .onStatus(
                      HttpStatus.INTERNAL_SERVER_ERROR::equals,
                      response -> response.bodyToMono(String.class).map(Exception::new))
                  .onStatus(
                      HttpStatus.BAD_REQUEST::equals,
                      response -> response.bodyToMono(String.class).map(Exception::new))
                  .bodyToMono(ResponseDiscoverMoviesApi.class)
                  .block())
          .map(ResponseDiscoverMoviesApi::getResults);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestRepositoryException("Model is required");
    }
  }
}
