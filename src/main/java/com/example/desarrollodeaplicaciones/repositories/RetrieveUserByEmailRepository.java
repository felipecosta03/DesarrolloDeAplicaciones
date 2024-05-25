package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RetrieveUserByEmailRepository extends Repository<User, Long> {

  Optional<User> findUserByEmail(String email);
}
