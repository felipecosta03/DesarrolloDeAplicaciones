package com.example.desarrollodeaplicaciones.router;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public abstract class BaseRouter {

  protected final String getUserEmail() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
