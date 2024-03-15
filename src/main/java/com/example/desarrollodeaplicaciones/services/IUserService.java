package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAll();

    UserDTO add(UserDTO user);

    UserDTO findById(String id);
}
