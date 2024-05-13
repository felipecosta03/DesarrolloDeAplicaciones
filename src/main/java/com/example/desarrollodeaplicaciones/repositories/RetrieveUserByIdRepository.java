package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RetrieveUserByIdRepository extends Repository<User, Long> {
  Optional<User> findById(Long id);
}
