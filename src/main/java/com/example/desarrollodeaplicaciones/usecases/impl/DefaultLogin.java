package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.dtos.SessionDto;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.Login;
import com.example.desarrollodeaplicaciones.usecases.SaveUser;
import com.example.desarrollodeaplicaciones.usecases.VerifyGoogleToken;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogin implements Login {

  private final VerifyGoogleToken verifyGoogleToken;
  private final ExistsUserByEmail existsUserByEmail;
  private final SaveUser saveUser;

  public DefaultLogin(
      VerifyGoogleToken verifyGoogleToken, ExistsUserByEmail existsUserByEmail, SaveUser saveUser) {
    this.verifyGoogleToken = verifyGoogleToken;
    this.existsUserByEmail = existsUserByEmail;
    this.saveUser = saveUser;
  }

  @Override
  public SessionDto apply(GoogleTokenDto googleTokenDto) {
    User user = verifyGoogleToken.apply(googleTokenDto);

    if (!existsUserByEmail.apply(
        ExistsUserByEmail.Model.builder().email(user.getEmail()).build())) {
      saveUser.accept(user);
    }
    return null;
  }
}
