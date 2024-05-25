package com.example.desarrollodeaplicaciones.usecases.security.impl;

import com.example.desarrollodeaplicaciones.usecases.security.CleanToken;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DefaultCleanToken implements CleanToken {
  @Override
  public String apply(String authHeader) {
    if (!StringUtils.isNullOrEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
