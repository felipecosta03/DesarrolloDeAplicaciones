package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.getAll().stream().map(Mapper::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO add(UserDTO user) {
        userRepository.addUser(Mapper.userDtoToUser(user));
        return user;
    }

    @Override
    public UserDTO findById(String id) {
        return Mapper.userToUserDto(userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id)));
    }
}
