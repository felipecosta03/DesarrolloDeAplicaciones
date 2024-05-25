package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.dtos.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  List<Token> findAllByUserId(Long userId);

  Optional<Token> findByAccessToken(String token);

  Optional<Token> findByRefreshToken(String token);
}
