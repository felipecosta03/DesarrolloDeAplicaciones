package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.CreateAccessToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateRefreshToken;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreateAccessToken implements CreateAccessToken {

  private final Integer VALIDITY = 1000 * 60 * 15;

  @Override
  public String apply(String email, Key key) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + VALIDITY);
    return Jwts.builder().subject(email).issuedAt(now).expiration(validity).signWith(key).compact();
  }
}
