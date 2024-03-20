package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.OAuthUserDTO;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {
  @GetMapping("/loginSuccess")
  public OAuthUserDTO loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
    Map<String, Object> attributes = principal.getAttributes();
    return OAuthUserDTO.builder()
        .name(attributes.get("given_name").toString())
        .lastName(attributes.get("family_name").toString())
        .email(attributes.get("email").toString())
        .build();
  }
}
