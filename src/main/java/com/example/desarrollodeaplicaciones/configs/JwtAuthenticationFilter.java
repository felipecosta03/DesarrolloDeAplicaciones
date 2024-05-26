package com.example.desarrollodeaplicaciones.configs;

import com.example.desarrollodeaplicaciones.usecases.security.IsAccessTokenValid;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveTokenFromRequest;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveUsernameFromToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final IsAccessTokenValid isAccessTokenValid;
  private final RetrieveUsernameFromToken retrieveUsernameFromToken;
  private final RetrieveTokenFromRequest retrieveTokenFromRequest;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = retrieveTokenFromRequest.apply(request);
    if (token != null && isAccessTokenValid.test(token)) {
      String username = retrieveUsernameFromToken.apply(token);
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }
}
