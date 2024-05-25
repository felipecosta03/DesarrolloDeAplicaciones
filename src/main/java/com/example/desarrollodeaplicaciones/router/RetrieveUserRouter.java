package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.exceptions.repository.NotFoundRepositoryException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveUserRouter extends UserRouter {

  private final RetrieveUserResponse retrieveUserResponse;

  public RetrieveUserRouter(RetrieveUserResponse retrieveUserResponse) {
    this.retrieveUserResponse = retrieveUserResponse;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> get(@PathVariable("userId") Long userId) {
    return retrieveUserResponse
        .apply(RetrieveUserResponse.Model.builder().userId(userId).build())
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new NotFoundRepositoryException("User not found"));
  }
}
