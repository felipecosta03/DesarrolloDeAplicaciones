package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {
    User findByNickName(String nickname);
    User findByEmail(String email);
    boolean existsUserByEmail(String email);

    boolean existsUserByNickName(String nickname);
}
