package com.example.desarrollodeaplicaciones.configs;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private String secretKey = "qweqweqeqewqeqweqweqeqweqwewqsxzccooksadkoakow923012a1idaskda"; // Clave secreta para firmar el token

  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
