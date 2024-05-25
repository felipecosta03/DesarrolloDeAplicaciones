package com.example.desarrollodeaplicaciones.usecases.security.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.security.BuildJwtParser;
import com.example.desarrollodeaplicaciones.usecases.security.IsTokenValid;
import org.springframework.stereotype.Component;

@Component
public class DefaultIsTokenValid implements IsTokenValid {

  private final BuildJwtParser buildJwtParser;

  public DefaultIsTokenValid(BuildJwtParser buildJwtParser) {
    this.buildJwtParser = buildJwtParser;
  }

  @Override
  public boolean test(String token) {
    validateToken(token);
    try {
      buildJwtParser.get().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private void validateToken(String token) {
    if (isNull(token)) {
      throw new BadRequestUseCaseException("Token is required");
    }
  }
}
