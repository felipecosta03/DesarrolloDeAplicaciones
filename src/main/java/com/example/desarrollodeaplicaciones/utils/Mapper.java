package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.User;

public class Mapper {
    public static User userDtoToUser(UserDTO userDTO) {
        return User.builder().name(userDTO.getName()).lastName(userDTO.getLastName()).id(userDTO.getId()).build();
    }

    public static UserDTO userToUserDto(User user) {
        return UserDTO.builder().name(user.getName()).lastName(user.getLastName()).id(user.getId()).build();
    }
}
