package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.Token;
import java.util.function.Function;

public interface Refresh extends Function<String, Token> {}
