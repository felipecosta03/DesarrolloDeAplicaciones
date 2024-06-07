package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RetrieveUserRouter extends UserRouter {

  private final RetrieveUserResponse retrieveUserResponse;

  public RetrieveUserRouter(RetrieveUserResponse retrieveUserResponse) {
    this.retrieveUserResponse = retrieveUserResponse;
  }

  @GetMapping
  public ResponseEntity<UserDto> get() {
    return retrieveUserResponse
        .apply(RetrieveUserResponse.Model.builder().userId(getUserId()).build())
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new NotFoundRepositoryException("User not found"));
  }
}
