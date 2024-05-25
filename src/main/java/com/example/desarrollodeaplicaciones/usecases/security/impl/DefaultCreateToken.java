package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.security.CreateAccessToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateRefreshToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateToken;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveJwtKey;
import java.security.Key;
import org.springframework.stereotype.Component;

@Component
public class DefaultCreateToken implements CreateToken {

  private final CreateAccessToken createAccessToken;
  private final CreateRefreshToken createRefreshToken;
  private final RetrieveJwtKey retrieveJwtKey;

  public DefaultCreateToken(
      CreateAccessToken createAccessToken,
      CreateRefreshToken createRefreshToken,
      RetrieveJwtKey retrieveJwtKey) {
    this.createAccessToken = createAccessToken;
    this.createRefreshToken = createRefreshToken;
    this.retrieveJwtKey = retrieveJwtKey;
  }

  @Override
  public Token apply(User user) {
    final Key key = retrieveJwtKey.get();
    return Token.builder()
        .accessToken(createAccessToken.apply(user.getEmail(), key))
        .refreshToken(createRefreshToken.apply(user.getEmail(), key))
        .userId(user.getId())
        .loggedOut(false)
        .build();
  }
}
