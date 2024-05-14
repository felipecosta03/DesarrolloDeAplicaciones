package com.example.desarrollodeaplicaciones.configs;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
  private final String movieAuthToken;

  public WebClientConfig(@Value("${MOVIE_AUTH_TOKEN}") String movieAuthToken) {
    this.movieAuthToken = movieAuthToken;
  }

  @Bean
  public WebClient webClient() {
    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000).disableRetry(true);

    return WebClient.builder()
        .baseUrl("https://api.themoviedb.org/3")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.AUTHORIZATION, movieAuthToken).clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
