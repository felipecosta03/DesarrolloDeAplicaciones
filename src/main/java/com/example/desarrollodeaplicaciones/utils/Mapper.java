package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.User;

public class Mapper {
  private Mapper() {}

  public static User userDtoToUser(UserDTO userDTO) {
    return User.builder()
        .name(userDTO.getName())
        .lastName(userDTO.getLastName())
        .id(userDTO.getId())
        .build();
  }

  public static UserDTO userToUserDto(User user) {
    return UserDTO.builder()
        .name(user.getName())
        .lastName(user.getLastName())
        .id(user.getId())
        .build();
  }

	public static Movie movieDtoToUser(MovieDTO movieDto) {
		return Movie.builder()
		.title(movieDto.getTitle())
		.subtitle(movieDto.getSubtitle())
		.synapsis(movieDto.getSynapsis())
		.genre(movieDto.getGenre())
		.image(movieDto.getImage())
		.trailer(movieDto.getTrailer())
		.releasedDate(movieDto.getReleasedDate())
		.duration(movieDto.getDuration())
		.director(movieDto.getDirector())
		.actors(movieDto.getActors())
		.build();
	}
}
