package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.function.Function;

public interface VerifyGoogleToken extends Function<GoogleTokenDto, User> {}
