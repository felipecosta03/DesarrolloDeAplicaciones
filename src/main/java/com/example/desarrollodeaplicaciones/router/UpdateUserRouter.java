package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.UserUpdateDto;
import com.example.desarrollodeaplicaciones.usecases.UpdateUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserRouter extends UserRouter {
  private final UpdateUser updateUser;

  public UpdateUserRouter(UpdateUser updateUser) {
    this.updateUser = updateUser;
  }

  @PutMapping
  public ResponseEntity<Void> update(@RequestBody UserUpdateDto userDto) {
    updateUser.accept(getUserId(), userDto);
    return ResponseEntity.ok().build();
  }
}
