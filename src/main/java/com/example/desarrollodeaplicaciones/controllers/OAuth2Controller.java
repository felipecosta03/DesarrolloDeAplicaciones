package com.example.desarrollodeaplicaciones.controllers;

import java.util.Map;

import com.example.desarrollodeaplicaciones.dtos.JwtTokenDTO;
import com.example.desarrollodeaplicaciones.services.IOAuth2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

  private final IOAuth2Service oAuth2Service;

  public OAuth2Controller(IOAuth2Service oAuth2Service) {
    this.oAuth2Service = oAuth2Service;
  }

  @GetMapping("/loginSuccess")
  public ResponseEntity<JwtTokenDTO> loginSuccess(@AuthenticationPrincipal DefaultOidcUser principal) {
    return ResponseEntity.ok(oAuth2Service.registerUser(principal));
  }
}
