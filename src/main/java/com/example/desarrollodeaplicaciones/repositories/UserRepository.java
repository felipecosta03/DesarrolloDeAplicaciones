package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository{
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public List<User> getAll(){
        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

}
