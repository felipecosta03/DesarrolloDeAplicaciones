package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.repository.Repository;

public interface ExistUserByIdRepository extends Repository<User, Long> {
  boolean existsById(Long userId);
}
