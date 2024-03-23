package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.JwtTokenDTO;
import com.example.desarrollodeaplicaciones.exceptions.EmailAlreadyExistsException;
import com.example.desarrollodeaplicaciones.exceptions.UserRegistrationException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import java.util.Map;
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
    if(!userRepository.existsUserByEmail(principal.getEmail())){
      User user = User.builder().email(principal.getEmail()).name(principal.getName()).build();
      userRepository.save(user);
    }
    return JwtTokenDTO.builder().token(principal.getIdToken().getTokenValue()).build();
  }


}
