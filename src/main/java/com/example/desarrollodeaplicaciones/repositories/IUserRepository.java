package com.example.desarrollodeaplicaciones.repositories;


import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
}
