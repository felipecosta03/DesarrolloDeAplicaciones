package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.BuildJwtParser;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveUsernameFromToken;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveUsernameFromToken implements RetrieveUsernameFromToken {

  private final BuildJwtParser buildJwtParser;

  public DefaultRetrieveUsernameFromToken(BuildJwtParser buildJwtParser) {
    this.buildJwtParser = buildJwtParser;
  }

  @Override
  public String apply(String token) {
    return buildJwtParser.get().parseClaimsJws(token).getBody().getSubject();
  }
}
