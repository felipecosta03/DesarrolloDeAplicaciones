package com.example.desarrollodeaplicaciones.usecases.security;

import io.jsonwebtoken.JwtParser;
import java.util.function.Supplier;

@FunctionalInterface
public interface BuildJwtParser extends Supplier<JwtParser> {}
