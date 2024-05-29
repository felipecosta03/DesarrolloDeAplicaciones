package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.repository.Repository;

public interface DeleteUserRepository extends Repository<User, Long> {
  void deleteById(Long userId);
}
