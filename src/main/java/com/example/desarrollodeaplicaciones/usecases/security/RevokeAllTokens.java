package com.example.desarrollodeaplicaciones.usecases.security;

import java.util.function.Consumer;

@FunctionalInterface
public interface RevokeAllTokens extends Consumer<Long> {}
