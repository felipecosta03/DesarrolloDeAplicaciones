package com.example.desarrollodeaplicaciones.usecases.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RetrieveTokenFromRequest extends Function<HttpServletRequest, String> {}
