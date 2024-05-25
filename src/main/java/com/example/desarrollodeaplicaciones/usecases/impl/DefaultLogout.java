package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.Logout;
import com.example.desarrollodeaplicaciones.usecases.security.RevokeAllTokens;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogout implements Logout {

  private final RevokeAllTokens revokeAllTokens;

  public DefaultLogout(RevokeAllTokens revokeAllTokens) {
    this.revokeAllTokens = revokeAllTokens;
  }

  @Override
  public void accept(String email) {
    if (Strings.isNullOrEmpty(email)) {
      throw new BadRequestUseCaseException("Email is required");
    }
    revokeAllTokens.accept(email);
  }
}
