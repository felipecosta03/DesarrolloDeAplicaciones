package com.example.desarrollodeaplicaciones.usecases.security;

import java.util.function.Predicate;

@FunctionalInterface
public interface IsTokenValid extends Predicate<String> {
}
