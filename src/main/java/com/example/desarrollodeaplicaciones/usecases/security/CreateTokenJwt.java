package com.example.desarrollodeaplicaciones.usecases.security;

import com.example.desarrollodeaplicaciones.models.User;
import java.util.function.Function;

@FunctionalInterface
public interface CreateTokenJwt extends Function<User, String> {}
