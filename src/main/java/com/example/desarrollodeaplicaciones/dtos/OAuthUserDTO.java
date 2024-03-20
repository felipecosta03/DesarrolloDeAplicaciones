package com.example.desarrollodeaplicaciones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUserDTO {
    private String name;
    private String lastName;
    private String email;
}
