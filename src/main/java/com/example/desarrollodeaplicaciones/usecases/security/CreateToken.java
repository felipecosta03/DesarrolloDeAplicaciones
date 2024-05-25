package com.example.desarrollodeaplicaciones.usecases.security;

import com.example.desarrollodeaplicaciones.dtos.Token;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.function.Function;

@FunctionalInterface
public interface CreateToken extends Function<User, Token> {}
