package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.CreateRefreshToken;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreateRefreshToken implements CreateRefreshToken {

  @Override
  public String apply(Long id, Key key) {
    final long expirationTime = 1000L * 3600 * 24 * 7 * 4;
    Date now = new Date();
    Date validity = new Date(now.getTime() + expirationTime);
    return Jwts.builder()
        .subject(id.toString())
        .issuedAt(now)
        .expiration(validity)
        .signWith(key)
        .compact();
  }
}
