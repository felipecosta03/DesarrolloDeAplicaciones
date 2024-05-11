package com.example.desarrollodeaplicaciones.repositories.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseDiscoverMoviesApi;
import com.example.desarrollodeaplicaciones.repositories.RetrieveMoviesByGenreApiRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DefaultRetrieveMoviesByGenreApiRepository
    implements RetrieveMoviesByGenreApiRepository {

  private final WebClient webClient;

  public DefaultRetrieveMoviesByGenreApiRepository(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    final String sort = getSort(model.getDateOrder(), model.getQualificationOrder());
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
                              .queryParam("sort_by", sort)
                              .queryParam("with_genres", model.getGenreId())
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

  private String getSort(String dateOrder, String qualificationOrder) {
    if (!isNull(qualificationOrder)) {
      if (qualificationOrder.equals("asc")) {
        return "vote_average.asc";
      } else {
        return "vote_average.desc";
      }
    }
    if (!isNull(dateOrder) && dateOrder.equals("asc")) {
      return "release_date.asc";
    }
    return "release_date.desc";
  }
}
