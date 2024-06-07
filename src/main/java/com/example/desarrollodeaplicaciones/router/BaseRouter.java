package com.example.desarrollodeaplicaciones.router;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
public abstract class BaseRouter {

  protected final Long getUserId() {
    return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  protected HttpServletRequest getRequest() {
    return ((ServletRequestAttributes)
            Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
        .getRequest();
  }

  protected String getToken() {
    return getRequest().getHeader(HttpHeaders.AUTHORIZATION);
  }
}
