package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.security.CreateTokenJwt;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveJwtKey;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreateTokenJwt implements CreateTokenJwt {

  public static final int VALIDITY = 3600 * 1000 * 3; // 3 hours
  private final RetrieveJwtKey retrieveJwtKey;

  public DefaultCreateTokenJwt(RetrieveJwtKey retrieveJwtKey) {
    this.retrieveJwtKey = retrieveJwtKey;
  }

  @Override
  public String apply(User user) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + VALIDITY);
    return Jwts.builder()
        .subject(user.getEmail())
        .issuedAt(now)
        .expiration(validity)
        .signWith(retrieveJwtKey.get())
        .compact();
  }
}
