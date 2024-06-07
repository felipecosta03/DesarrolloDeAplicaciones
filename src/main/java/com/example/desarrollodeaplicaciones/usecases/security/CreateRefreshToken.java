package com.example.desarrollodeaplicaciones.usecases.security;

import java.security.Key;
import java.util.function.BiFunction;

public interface CreateRefreshToken extends BiFunction<Long, Key, String> {}
