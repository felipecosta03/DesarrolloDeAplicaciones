package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.dtos.SessionDto;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.Login;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserByEmail;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import com.example.desarrollodeaplicaciones.usecases.VerifyGoogleToken;
import com.example.desarrollodeaplicaciones.usecases.security.CreateTokenJwt;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultLogin implements Login {

  private final VerifyGoogleToken verifyGoogleToken;
  private final SaveUser saveUser;
  private final CreateTokenJwt createTokenJwt;
  private final RetrieveUserByEmail retrieveUserByEmail;

  public DefaultLogin(
      VerifyGoogleToken verifyGoogleToken,
      SaveUser saveUser,
      CreateTokenJwt createTokenJwt,
      RetrieveUserByEmail retrieveUserByEmail) {
    this.verifyGoogleToken = verifyGoogleToken;
    this.saveUser = saveUser;
    this.createTokenJwt = createTokenJwt;
    this.retrieveUserByEmail = retrieveUserByEmail;
  }

  @Override
  public SessionDto apply(GoogleTokenDto googleTokenDto) {
    User user = verifyGoogleToken.apply(googleTokenDto);

    Optional<User> userByEmail =
        retrieveUserByEmail.apply(
            RetrieveUserByEmail.Model.builder().email(user.getEmail()).build());
    if (userByEmail.isEmpty()) {
      user = saveUser.apply(user);
    } else {
      user = userByEmail.get();
    }
    return SessionDto.builder().sessionToken(createTokenJwt.apply(user)).build();
  }
}
