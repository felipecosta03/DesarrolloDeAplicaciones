package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.CleanToken;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveTokenFromRequest;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveTokenFromRequest implements RetrieveTokenFromRequest {

  private final CleanToken cleanToken;

  public DefaultRetrieveTokenFromRequest(CleanToken cleanToken) {
    this.cleanToken = cleanToken;
  }

  @Override
  public String apply(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    return cleanToken.apply(authHeader);
  }
}
