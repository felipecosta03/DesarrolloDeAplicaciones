package com.example.desarrollodeaplicaciones.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
  private final String movieAuthToken;

  public WebClientConfig(@Value("${MOVIE_AUTH_TOKEN}") String movieAuthToken) {
    this.movieAuthToken = movieAuthToken;
  }

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl("https://api.themoviedb.org/3")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.AUTHORIZATION, movieAuthToken)
        .build();
  }
}
