package com.example.desarrollodeaplicaciones.usecases.security.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.InternalServerErrorUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveJwtKey;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveJwtKey implements RetrieveJwtKey {
  private final Environment env;

  public DefaultRetrieveJwtKey(Environment env) {
    this.env = env;
  }

  @Override
  public Key get() {
    final String secret = env.getProperty("JWT_SECRET");
    if (isNull(secret) || secret.isBlank()) {
      throw new InternalServerErrorUseCaseException("JWT_SECRET is required");
    }
    return Keys.hmacShaKeyFor(secret.getBytes());
  }
}
