package com.example.desarrollodeaplicaciones.usecases.impl;

import com.example.desarrollodeaplicaciones.usecases.Logout;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogout implements Logout {
  @Override
  public void accept(String email) {}
}
