package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.exceptions.ForbiddenUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.TokenRepository;
import com.example.desarrollodeaplicaciones.usecases.Refresh;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserById;
import com.example.desarrollodeaplicaciones.usecases.security.CleanToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateToken;
import com.example.desarrollodeaplicaciones.usecases.security.IsRefreshTokenValid;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveUsernameFromToken;
import com.example.desarrollodeaplicaciones.usecases.security.RevokeAllTokens;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

@Component
public class DefaultRefresh implements Refresh {

  private final CleanToken cleanToken;
  private final IsRefreshTokenValid isRefreshTokenValid;
  private final TokenRepository tokenRepository;
  private final RevokeAllTokens revokeAllTokens;
  private final RetrieveUsernameFromToken retrieveUsernameFromToken;
  private final CreateToken createToken;
  private final RetrieveUserById retrieveUserById;

  public DefaultRefresh(
      CleanToken cleanToken,
      IsRefreshTokenValid isRefreshTokenValid,
      TokenRepository tokenRepository,
      RevokeAllTokens revokeAllTokens,
      RetrieveUsernameFromToken retrieveUsernameFromToken,
      CreateToken createToken,
      RetrieveUserById retrieveUserById) {
    this.cleanToken = cleanToken;
    this.isRefreshTokenValid = isRefreshTokenValid;
    this.tokenRepository = tokenRepository;
    this.revokeAllTokens = revokeAllTokens;
    this.retrieveUsernameFromToken = retrieveUsernameFromToken;
    this.createToken = createToken;
    this.retrieveUserById = retrieveUserById;
  }

  @Override
  public Token apply(String authToken) {
    if (Strings.isNullOrEmpty(authToken)) {
      throw new ForbiddenUseCaseException("Invalid token");
    }
    String token = cleanToken.apply(authToken);
    Long userId = Long.valueOf(retrieveUsernameFromToken.apply(token));
    if (isRefreshTokenValid.test(token)) {
      revokeAllTokens.accept(userId);
    } else {
      throw new ForbiddenUseCaseException("Invalid token");
    }

    User user = retrieveUserById.apply(RetrieveUserById.Model.builder().userId(userId).build());
    return tokenRepository.save(createToken.apply(user));
  }
}
