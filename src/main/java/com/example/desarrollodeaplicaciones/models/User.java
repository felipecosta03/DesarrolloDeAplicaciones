package com.example.desarrollodeaplicaciones.models;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Valid
@Builder
public class User {
    private String id;
    private String name;
    private String lastName;
}
