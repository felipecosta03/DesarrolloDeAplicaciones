package com.example.desarrollodeaplicaciones.usecases.security;

import java.security.Key;
import java.util.function.Supplier;

public interface RetrieveJwtKey extends Supplier<Key> {}
