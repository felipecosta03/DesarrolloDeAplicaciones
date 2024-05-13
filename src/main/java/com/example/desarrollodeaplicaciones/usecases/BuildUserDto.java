package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.UserDto;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.function.Function;

public interface BuildUserDto extends Function<User, UserDto> {}
