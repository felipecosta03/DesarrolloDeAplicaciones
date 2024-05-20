package com.example.desarrollodeaplicaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
public class DesarrolloDeAplicacionesApplication{

  public static void main(String[] args) {
    SpringApplication.run(DesarrolloDeAplicacionesApplication.class, args);
  }

}
