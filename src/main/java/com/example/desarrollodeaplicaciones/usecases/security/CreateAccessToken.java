package com.example.desarrollodeaplicaciones.usecases.security;

import java.security.Key;
import java.util.function.BiFunction;

public interface CreateAccessToken extends BiFunction<String, Key, String> {}
