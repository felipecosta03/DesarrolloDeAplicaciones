package com.example.desarrollodeaplicaciones.router;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.exceptions.router.BadRequestRouterException;
import com.example.desarrollodeaplicaciones.usecases.security.CleanToken;
import com.example.desarrollodeaplicaciones.usecases.security.RetrieveUsernameFromToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
public abstract class BaseRouter {

  @Autowired private CleanToken cleanToken;
  @Autowired private RetrieveUsernameFromToken retrieveUsernameFromToken;

  protected final HttpServletRequest getRequest() {
    return ((ServletRequestAttributes)
            Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
        .getRequest();
  }

  protected final String getUserEmail() {
    String token = getToken();
    if (isNull(token)) {
      throw new BadRequestRouterException("Token not found");
    }
    return retrieveUsernameFromToken.apply(token);
  }

  protected String getToken() {
    return cleanToken.apply(getRequest().getHeader(HttpHeaders.AUTHORIZATION));
  }
}
