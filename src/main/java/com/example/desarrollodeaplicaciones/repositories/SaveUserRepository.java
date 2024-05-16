package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.repository.Repository;

public interface SaveUserRepository extends Repository<User, Long> {
  void save(User user);
}
