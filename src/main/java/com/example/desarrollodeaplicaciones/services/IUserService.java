package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
  List<UserDTO> getAll();

  UserDTO add(UserDTO user);

  UserDTO findById(Long id);

  UserDTO updateUserImage(Long id, MultipartFile image);
}
