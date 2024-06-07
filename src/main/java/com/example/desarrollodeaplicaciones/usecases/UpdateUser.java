package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.UserUpdateDto;
import java.util.function.BiConsumer;

public interface UpdateUser extends BiConsumer<Long, UserUpdateDto> {}
