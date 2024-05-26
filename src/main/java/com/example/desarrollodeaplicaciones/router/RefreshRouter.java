package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.usecases.Refresh;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshRouter extends AuthRouter {

  private final Refresh refresh;

  public RefreshRouter(Refresh refresh) {
    this.refresh = refresh;
  }

  @PostMapping("/refresh")
  public ResponseEntity<Token> post() {
    return ResponseEntity.ok(refresh.apply(getToken()));
  }
}
