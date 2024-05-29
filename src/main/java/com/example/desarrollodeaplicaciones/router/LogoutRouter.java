package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.usecases.Logout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutRouter extends AuthRouter {

  private final Logout logout;

  public LogoutRouter(Logout logout) {
    this.logout = logout;
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> post() {
    logout.accept(getUserEmail());
    return ResponseEntity.status(204).build();
  }
}
