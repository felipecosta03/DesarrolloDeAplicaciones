package com.example.desarrollodeaplicaciones.usecases.security;

import java.util.function.Function;

@FunctionalInterface
public interface RetrieveUsernameFromToken extends Function<String, String> {}
