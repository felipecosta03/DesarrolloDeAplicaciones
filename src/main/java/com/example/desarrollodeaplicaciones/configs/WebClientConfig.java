package com.example.desarrollodeaplicaciones.configs;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  private final Environment env;

  public WebClientConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public WebClient webClient() {
    HttpClient httpClient =
        HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000).disableRetry(true);

    return WebClient.builder()
        .baseUrl("https://api.themoviedb.org/3")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.AUTHORIZATION, env.getProperty("MOVIE_DB_AUTH_ENV"))
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
