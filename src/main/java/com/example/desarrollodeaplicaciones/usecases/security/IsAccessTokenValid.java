package com.example.desarrollodeaplicaciones.usecases.security;

import java.util.function.Predicate;

@FunctionalInterface
public interface IsAccessTokenValid extends Predicate<String> {}
