package com.example.desarrollodeaplicaciones.repositories.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.repository.BadRequestRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.FailedDependencyRepositoryException;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieCreditsApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviePeopleRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMoviePeopleRepository implements RetrieveMoviePeopleRepository {

  private final WebClient webClient;

  public DefaultRetrieveMoviePeopleRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<ResponseMovieCreditsApi> apply(RetrieveMoviePeopleRepository.Model model) {
    try {
      ResponseMovieCreditsApi response =
          webClient
              .get()
              .uri(
                  uriBuilder ->
                      uriBuilder
                          .path(String.format("/movie/%s/credits", model.getMovieId()))
                          .build())
              .retrieve()
              .onStatus(
                  HttpStatus.NOT_FOUND::equals,
                  clientResponse -> {
                    throw new NotFoundRepositoryException("Movie credits not found");
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
              .bodyToMono(ResponseMovieCreditsApi.class)
              .block();

      return translateCast(response);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private Optional<ResponseMovieCreditsApi> translateCast(ResponseMovieCreditsApi response) {
    if (isNull(response) || isNull(response.getCast()) || isNull(response.getCrew())) {
      return Optional.empty();
    }
    response
        .getCast()
        .forEach(
            cast -> {
              if (!isNull(cast) && "Acting".equalsIgnoreCase(cast.getKnownForDepartment())) {
                cast.setKnownForDepartment("Actor");
              }
            });

    return Optional.of(response);
  }
}
