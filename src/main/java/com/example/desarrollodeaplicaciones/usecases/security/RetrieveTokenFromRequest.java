package com.example.desarrollodeaplicaciones.usecases.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Function;

public interface RetrieveTokenFromRequest extends Function<HttpServletRequest, String> {}
