package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.repositories.TokenRepository;
import com.example.desarrollodeaplicaciones.usecases.security.RevokeAllTokens;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultRevokeAllTokens implements RevokeAllTokens {

  private final TokenRepository tokenRepository;

  public DefaultRevokeAllTokens(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  public void accept(Long userId) {
    List<Token> tokens = tokenRepository.findAllByUserId(userId);

    for (Token token : tokens) {
      token.setLoggedOut(true);
    }
    tokenRepository.saveAll(tokens);
  }
}
