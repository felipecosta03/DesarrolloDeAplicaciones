package com.example.desarrollodeaplicaciones.repositories.impl;

import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMovieImageRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMovieImageRepository implements RetrieveMovieImageRepository {

  private final WebClient webClient;

  public DefaultRetrieveMovieImageRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<ResponseMovieImagesApi> apply(RetrieveMovieImageRepository.Model model) {
    try {
      return Optional.ofNullable(
          webClient
              .get()
              .uri(
                  uriBuilder ->
                      uriBuilder
                          .path(String.format("/movie/%s/images", model.getMovieId()))
                          .build())
              .retrieve()
              .onStatus(
                  HttpStatus.NOT_FOUND::equals,
                  clientResponse -> {
                    throw new NotFoundRepositoryException("Movie images not found");
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
              .bodyToMono(ResponseMovieImagesApi.class)
              .block());
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
