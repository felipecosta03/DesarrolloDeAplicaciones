package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.repository.Repository;

public interface SaveUserRepository extends Repository<User, Long> {
  User save(User user);
}
