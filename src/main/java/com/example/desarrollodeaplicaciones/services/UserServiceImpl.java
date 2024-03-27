package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements IUserService {

  private final IUserRepository userRepository;
  private final IFilesStorage filesStorage;

  public UserServiceImpl(IUserRepository userRepository, IFilesStorage filesStorage) {
    this.userRepository = userRepository;
    this.filesStorage = filesStorage;
  }

  @Override
  public List<UserDTO> getAll() {
    return userRepository.findAll().stream().map(Mapper::userToUserDto).toList();
  }

  @Override
  public UserDTO add(UserDTO user) {
    userRepository.save(Mapper.userDtoToUser(user));
    return user;
  }

  @Override
  public UserDTO findById(Long id) {
    return Mapper.userToUserDto(getUserById(id));
  }

  private User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @Override
  public UserDTO updateUserImage(Long id, MultipartFile image) {
    User user = getUserById(id);
    byte[] imageBytes;
    try {
      imageBytes = image.getBytes();
    } catch (IOException e) {
      throw new RuntimeException("Error al obtener la imagen");
    }
    String imageUrl =
        filesStorage.uploadFile(
            "user-images", user.getId() + "-" + image.getOriginalFilename(), imageBytes);
    user.setImageUrl(imageUrl);
    userRepository.save(user);
    return Mapper.userToUserDto(user);
  }
}
