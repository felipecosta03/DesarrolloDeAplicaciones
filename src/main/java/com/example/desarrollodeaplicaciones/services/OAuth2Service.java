package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.JwtTokenDTO;
import com.example.desarrollodeaplicaciones.exceptions.InactiveUserException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service implements IOAuth2Service {

  private final IUserRepository userRepository;

  public OAuth2Service(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public JwtTokenDTO registerUser(DefaultOidcUser principal) {
    User user =
        userRepository
            .findByEmail(principal.getEmail())
            .orElseGet(
                () -> {
                  User newUser =
                      User.builder()
                          .email(principal.getEmail())
                          .name(principal.getGivenName())
                          .lastName(principal.getFamilyName())
                          .nickName(principal.getEmail())
                          .active(true)
                          .build();
                  userRepository.save(newUser);
                  return newUser;
                });
    if (!user.isActive()) {
      throw new InactiveUserException();
    }
    return JwtTokenDTO.builder().token(principal.getIdToken().getTokenValue()).build();
  }
}
