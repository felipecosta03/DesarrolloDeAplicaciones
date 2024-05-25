package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.TokenRepository;
import com.example.desarrollodeaplicaciones.usecases.Login;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserByEmail;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import com.example.desarrollodeaplicaciones.usecases.VerifyGoogleToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateToken;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultLogin implements Login {

  private final VerifyGoogleToken verifyGoogleToken;
  private final SaveUser saveUser;
  private final CreateToken createToken;
  private final RetrieveUserByEmail retrieveUserByEmail;
  private final TokenRepository tokenRepository;

  public DefaultLogin(
      VerifyGoogleToken verifyGoogleToken,
      SaveUser saveUser,
      CreateToken createToken,
      RetrieveUserByEmail retrieveUserByEmail,
      TokenRepository tokenRepository) {
    this.verifyGoogleToken = verifyGoogleToken;
    this.saveUser = saveUser;
    this.createToken = createToken;
    this.retrieveUserByEmail = retrieveUserByEmail;
    this.tokenRepository = tokenRepository;
  }

  @Override
  public Token apply(GoogleTokenDto googleTokenDto) {
    User user = verifyGoogleToken.apply(googleTokenDto);

    Optional<User> userByEmail =
        retrieveUserByEmail.apply(
            RetrieveUserByEmail.Model.builder().email(user.getEmail()).build());
    if (userByEmail.isEmpty()) {
      user = saveUser.apply(user);
    } else {
      user = userByEmail.get();
    }
    Token token = createToken.apply(user);
    tokenRepository.save(token);
    return token;
  }
}
