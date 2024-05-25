package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.usecases.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRouter extends AuthRouter {

  private final Login login;

  public LoginRouter(Login login) {
    this.login = login;
  }

  @PostMapping("/login")
  public ResponseEntity<Token> post(@RequestBody GoogleTokenDto googleToken) {
    return ResponseEntity.ok(login.apply(googleToken));
  }
}
