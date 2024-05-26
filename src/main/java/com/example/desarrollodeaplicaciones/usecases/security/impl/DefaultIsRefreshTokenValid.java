package com.example.desarrollodeaplicaciones.usecases.security.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.repositories.TokenRepository;
import com.example.desarrollodeaplicaciones.usecases.security.BuildJwtParser;
import com.example.desarrollodeaplicaciones.usecases.security.IsRefreshTokenValid;
import org.springframework.stereotype.Component;

@Component
public class DefaultIsRefreshTokenValid implements IsRefreshTokenValid {

  private final BuildJwtParser buildJwtParser;
  private final TokenRepository tokenRepository;

  public DefaultIsRefreshTokenValid(
      BuildJwtParser buildJwtParser, TokenRepository tokenRepository) {
    this.buildJwtParser = buildJwtParser;
    this.tokenRepository = tokenRepository;
  }

  @Override
  public boolean test(String token) {
    validateToken(token);
    try {
      buildJwtParser.get().parseClaimsJws(token);
      return tokenRepository.findByRefreshToken(token).map(t -> !t.isLoggedOut()).orElse(false);
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
