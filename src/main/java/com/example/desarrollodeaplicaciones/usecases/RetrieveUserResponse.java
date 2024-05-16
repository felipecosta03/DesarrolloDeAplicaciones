package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;

public interface RetrieveUserResponse
    extends Function<RetrieveUserResponse.Model, Optional<UserDto>> {
  @Getter
  @Builder
  class Model {
    private Long userId;
  }
}
