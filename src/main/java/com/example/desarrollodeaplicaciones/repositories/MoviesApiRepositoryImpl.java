package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseCreditsApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseDiscoverMoviesApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseGenreApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieImagesApi;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseMovieVideo;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class MoviesApiRepositoryImpl {
  private final WebClient webClient;

  public MoviesApiRepositoryImpl(WebClient webClient) {
    this.webClient = webClient;
  }

  public ResponseDiscoverMoviesApi getMoviesByPage(
      Integer page, Optional<String> dateOrder, Optional<String> qualificationOrder) {
    String sort;

    if (dateOrder.isPresent()) {
      sort = String.format("primary_release_date.%s", dateOrder.get());
    } else if (qualificationOrder.isPresent()) {
      sort = String.format("vote_average.%s", qualificationOrder.get());
    } else {
      sort = "primary_release_date.desc";
    }
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/discover/movie")
                    .queryParam("language", "es-es")
                    .queryParam("page", page)
                    .queryParam("primary_release_date", LocalDate.now())
                    .queryParam("sort_by", sort)
                    .build())
        .retrieve() // TODO Crear excepciones personalizadas
        .onStatus(
            HttpStatus.INTERNAL_SERVER_ERROR::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .onStatus(
            HttpStatus.BAD_REQUEST::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .bodyToMono(ResponseDiscoverMoviesApi.class)
        .block();
  }

  public MovieDetail getMovieById(Long movieId) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(String.format("/movie/%s", movieId))
                    .queryParam("language", "es-es")
                    .build())
        .retrieve() // TODO Crear excepciones personalizadas
        .onStatus(
            HttpStatus.INTERNAL_SERVER_ERROR::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .onStatus(
            HttpStatus.NOT_FOUND::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .bodyToMono(MovieDetail.class)
        .block();
  }

  public MovieSimple getMovieSimpleById(Long movieId) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder.path(String.format("/movie/%s?language=es-es", movieId)).build())
        .retrieve() // TODO Crear excepciones personalizadas
        .onStatus(
            HttpStatus.INTERNAL_SERVER_ERROR::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .onStatus(
            HttpStatus.NOT_FOUND::equals,
            response -> response.bodyToMono(String.class).map(Exception::new))
        .bodyToMono(MovieSimple.class)
        .block();
  }

  public ResponseMovieImagesApi getMovieImages(Long movieId) {
    return webClient
        .get() // TODO Crear excepciones personalizadas
        .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s/images", movieId)).build())
        .retrieve()
        .bodyToMono(ResponseMovieImagesApi.class)
        .block();
  }

  public ResponseMovieVideo getMovieVideos(Long movieId) {
    return webClient
        .get() // TODO Crear excepciones personalizadas
        .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s/videos", movieId)).build())
        .retrieve()
        .bodyToMono(ResponseMovieVideo.class)
        .block();
  }

  public ResponseCreditsApi getMovieCredits(Long movieId) {
    return webClient
        .get() // TODO Crear excepciones personalizadas
        .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s/credits", movieId)).build())
        .retrieve()
        .bodyToMono(ResponseCreditsApi.class)
        .block();
  }

  public boolean existsMovie(Long movieId) {
    try {
      HttpStatusCode status =
          webClient
              .get()
              .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s", movieId)).build())
              .exchangeToMono(response -> Mono.just(response.statusCode()))
              .block();
      return !HttpStatus.NOT_FOUND.equals(status);
    } catch (Exception e) {
      return false;
    }
  }

  public ResponseGenreApi getGenres() {
    return webClient
        .get() // TODO Crear excepciones personalizadas
        .uri(uriBuilder -> uriBuilder.path("/genre/movie/list?language=es").build())
        .retrieve()
        .bodyToMono(ResponseGenreApi.class)
        .block();
  }
}
