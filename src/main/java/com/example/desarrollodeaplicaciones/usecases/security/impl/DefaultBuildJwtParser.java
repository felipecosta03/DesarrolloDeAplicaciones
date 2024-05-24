package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.BuildJwtParser;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveJwtKey;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildJwtParser implements BuildJwtParser {

  private final RetrieveJwtKey retrieveJwtKey;

  public DefaultBuildJwtParser(final RetrieveJwtKey retrieveJwtKey) {
    this.retrieveJwtKey = retrieveJwtKey;
  }

  @Override
  public JwtParser get() {
    return Jwts.parser().setSigningKey(retrieveJwtKey.get()).build();
  }
}
