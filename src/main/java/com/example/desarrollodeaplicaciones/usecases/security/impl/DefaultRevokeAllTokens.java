package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.exceptions.usecases.NotFoundUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.TokenRepository;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserByEmail;
import com.example.desarrollodeaplicaciones.usecases.security.RevokeAllTokens;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultRevokeAllTokens implements RevokeAllTokens {

  private final RetrieveUserByEmail retrieveUserByEmail;
  private final TokenRepository tokenRepository;

  public DefaultRevokeAllTokens(
      RetrieveUserByEmail retrieveUserByEmail, TokenRepository tokenRepository) {
    this.retrieveUserByEmail = retrieveUserByEmail;
    this.tokenRepository = tokenRepository;
  }

  @Override
  public void accept(String userEmail) {
    User user =
        retrieveUserByEmail
            .apply(RetrieveUserByEmail.Model.builder().email(userEmail).build())
            .orElseThrow(() -> new NotFoundUseCaseException("User not found"));

    List<Token> tokens = tokenRepository.findAllByUserId(user.getId());

    for (Token token : tokens) {
      token.setLoggedOut(true);
    }

    tokenRepository.saveAll(tokens);
  }
}
