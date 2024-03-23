package com.example.desarrollodeaplicaciones.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document
public class User {
  @Id
  private String id;
  private String name;
  private String lastName;

  @Indexed(unique = true)
  private String nickName;

  @Indexed(unique = true)
  private String email;

  private String imageUrl;
  private boolean active;
}
