package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieDetailApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.response.ResponseDiscoverMoviesApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.response.ResponseMovieImagesApiDTO;
import java.time.LocalDate;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.response.ResponseMovieVideoDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class MoviesApiRepositoryImpl {
  private final WebClient webClient;

  public MoviesApiRepositoryImpl(WebClient webClient) {
    this.webClient = webClient;
  }

  public ResponseDiscoverMoviesApiDTO getMoviesByPage(Integer page) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/discover/movie")
                    .queryParam("page", page)
                    .queryParam("primary_release_date", LocalDate.now())
                    .build())
        .retrieve()
        .bodyToMono(ResponseDiscoverMoviesApiDTO.class)
        .block();
  }

  public MovieDetailApiDTO getMovieById(Integer movieId) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s", movieId)).build())
        .retrieve()
        .bodyToMono(MovieDetailApiDTO.class)
        .block();
  }

  public ResponseMovieImagesApiDTO getMovieImages(Integer movieId) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s/images", movieId)).build())
        .retrieve()
        .bodyToMono(ResponseMovieImagesApiDTO.class)
        .block();
  }

  public ResponseMovieVideoDTO getMovieVideos(Integer movieId) {
    return webClient
            .get()
            .uri(uriBuilder -> uriBuilder.path(String.format("/movie/%s/videos", movieId)).build())
            .retrieve()
            .bodyToMono(ResponseMovieVideoDTO.class)
            .block();
  }
}
