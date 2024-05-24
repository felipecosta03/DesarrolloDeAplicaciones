package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.RetrieveTokenFromRequest;
import com.google.common.net.HttpHeaders;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveTokenFromRequest implements RetrieveTokenFromRequest {

  @Override
  public String apply(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (!StringUtils.isNullOrEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
