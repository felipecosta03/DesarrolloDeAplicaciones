package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.usecases.DeleteUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteUserRouter extends UserRouter {

  private final DeleteUser deleteUserRouter;

  public DeleteUserRouter(DeleteUser deleteUserRouter) {
    this.deleteUserRouter = deleteUserRouter;
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> delete(@PathVariable Long userId) {
    deleteUserRouter.accept(DeleteUser.Model.builder().userId(userId).build());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
