package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.dtos.GoogleTokenDto;
import com.example.desarrollodeaplicaciones.dtos.SessionDto;
import java.util.function.Function;

public interface Login extends Function<GoogleTokenDto, SessionDto> {}
