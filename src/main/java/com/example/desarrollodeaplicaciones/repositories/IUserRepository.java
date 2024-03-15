package com.example.desarrollodeaplicaciones.repositories;


import com.example.desarrollodeaplicaciones.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    void addUser(User user);

    List<User> getAll();

    Optional<User> findById(String id);
}
