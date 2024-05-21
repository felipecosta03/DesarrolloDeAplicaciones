package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.repository.Repository;

public interface ExistsUserByEmailRepository extends Repository<User, Long> {

    boolean existsByEmail(String email);
}
