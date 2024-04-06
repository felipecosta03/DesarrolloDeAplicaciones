package com.example.desarrollodeaplicaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DesarrolloDeAplicacionesApplication {
  public static void main(String[] args) {
    SpringApplication.run(DesarrolloDeAplicacionesApplication.class, args);
  }
}
