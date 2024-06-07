package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.exceptions.ForbiddenUseCaseException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.usecases.VerifyGoogleToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DefaultVerifyGoogleToken implements VerifyGoogleToken {

  private final Environment env;

  public DefaultVerifyGoogleToken(Environment env) {
    this.env = env;
  }

  @Override
  public User apply(GoogleTokenDto googleTokenDto) {

    GoogleIdTokenVerifier verifier =
        new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList(env.getProperty("GOOGLE_CLIENT_ID")))
            .build();

    // (Receive idTokenString by HTTPS POST)

    GoogleIdToken idToken = null;
    try {
      idToken = verifier.verify(googleTokenDto.getGoogleToken());
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (idToken != null) {
      GoogleIdToken.Payload payload = idToken.getPayload();

      String email = payload.getEmail();
      String name = (String) payload.get("name");
      String pictureUrl = (String) payload.get("picture");
      String familyName = (String) payload.get("family_name");
      String givenName = (String) payload.get("given_name");

      return User.builder()
          .email(email)
          .name(givenName)
          .lastName(familyName)
          .image(pictureUrl)
          .nickName(buildNickName(name))
          .build();
    } else {
      throw new ForbiddenUseCaseException("Invalid Google ID token.");
    }
  }

  private String buildNickName(String name) {
    return name.replace(" ", "").toLowerCase();
  }
}
