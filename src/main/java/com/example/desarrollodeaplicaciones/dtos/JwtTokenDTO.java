package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenDTO {
    private String token;
}
