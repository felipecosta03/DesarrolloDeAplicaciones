package com.example.desarrollodeaplicaciones.services;


import com.example.desarrollodeaplicaciones.dtos.JwtTokenDTO;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public interface IOAuth2Service {

    JwtTokenDTO registerUser(DefaultOidcUser principal);
}
