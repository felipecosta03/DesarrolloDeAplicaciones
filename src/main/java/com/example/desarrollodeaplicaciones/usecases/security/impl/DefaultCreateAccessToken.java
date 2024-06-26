package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.CreateAccessToken;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreateAccessToken implements CreateAccessToken {

  @Override
  public String apply(Long id, Key key) {
    final int expirationTime = 1000 * 60 * 5;
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
