package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.exceptions.UserImageNotFound;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.utils.Mapper;
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
  public StatusDTO updateUserImage(Long id, MultipartFile image) {
    User user = getUserById(id);
    user.setImage(filesStorage.uploadFile(image));
    userRepository.save(user);
    return StatusDTO.builder().status(200).build();
  }

  private void deleteImageFromUser(User user) {
    filesStorage.deleteFile(user.getImage().getId());
    user.setImage(null);
    userRepository.save(user);
  }

  @Override
  public StatusDTO deleteImage(Long id) {
    User user = getUserById(id);
    if (user.getImage() == null) {
      throw new UserImageNotFound();
    }
    deleteImageFromUser(user);
    return StatusDTO.builder().status(200).build();
  }
}
