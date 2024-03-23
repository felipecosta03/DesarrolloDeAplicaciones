package com.example.desarrollodeaplicaciones.models;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Valid
@Builder
@Document
public class User {
  private String id;
  private String name;
  private String lastName;
  private String email;
  private String nickName;
}
