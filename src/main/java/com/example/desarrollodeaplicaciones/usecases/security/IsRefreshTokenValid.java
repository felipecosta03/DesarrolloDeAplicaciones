package com.example.desarrollodeaplicaciones.usecases.security;

import java.util.function.Predicate;

@FunctionalInterface
public interface IsRefreshTokenValid extends Predicate<String> {}
