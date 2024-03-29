package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.MediaDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.Media;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Person;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.ArrayList;
import java.util.Optional;

public class Mapper {
  private Mapper() {}

  public static UserDTO userToUserDto(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .lastName(user.getLastName())
        .nickName(user.getNickName())
        .email(user.getEmail())
        .image(mediaToMediaDto(user.getImage()))
        .active(user.isActive())
        .favoriteMovies(
            Optional.ofNullable(user.getFavoriteMovies())
                .map(movies -> movies.stream().map(Mapper::movieToMovieDTO).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  public static User userDtoToUser(UserDTO userDto) {
    return User.builder()
        .id(userDto.getId())
        .name(userDto.getName())
        .lastName(userDto.getLastName())
        .nickName(userDto.getNickName())
        .email(userDto.getEmail())
        .image(mediaDtoToMedia(userDto.getImage()))
        .active(userDto.isActive())
        .build();
  }

  public static MovieDTO movieToMovieDTO(Movie movie) {
    return MovieDTO.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .subtitle(movie.getSubtitle())
        .synapsis(movie.getSynapsis())
        .genre(movie.getGenre())
        .images(
            Optional.ofNullable(movie.getImages())
                .map(images -> images.stream().map(Mapper::mediaToMediaDto).toList())
                .orElse(new ArrayList<>()))
        .trailer(movie.getTrailer())
        .releaseDate(movie.getReleaseDate())
        .duration(movie.getDuration())
        .director(personToPersonDto(movie.getDirector()))
        .actors(
            Optional.ofNullable(movie.getActors())
                .map(actors -> actors.stream().map(Mapper::personToPersonDto).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  public static Media mediaDtoToMedia(MediaDTO mediaDto) {
    if (mediaDto == null) {
      return null;
    }
    return Media.builder().url(mediaDto.getUrl()).id(mediaDto.getId()).build();
  }

  public static MediaDTO mediaToMediaDto(Media media) {
    if (media == null) {
      return null;
    }
    return MediaDTO.builder().url(media.getUrl()).id(media.getId()).build();
  }

  public static Movie movieDtoToMovie(MovieDTO movieDto) {
    return Movie.builder()
        .id(movieDto.getId())
        .title(movieDto.getTitle())
        .subtitle(movieDto.getSubtitle())
        .synapsis(movieDto.getSynapsis())
        .genre(movieDto.getGenre())
        .images(
            Optional.ofNullable(movieDto.getImages())
                .map(images -> images.stream().map(Mapper::mediaDtoToMedia).toList())
                .orElse(new ArrayList<>()))
        .trailer(movieDto.getTrailer())
        .releasedDate(movieDto.getReleaseDate())
        .duration(movieDto.getDuration())
        .director(personDtoToPerson(movieDto.getDirector()))
        .actors(
            Optional.ofNullable(movieDto.getActors())
                .map(actors -> actors.stream().map(Mapper::personDtoToPerson).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  public static Person personDtoToPerson(PersonDTO personDto) {
    return Person.builder()
        .id(personDto.getId())
        .firstName(personDto.getFirstName())
        .lastName(personDto.getLastName())
        .build();
  }

  public static PersonDTO personToPersonDto(Person person) {
    return PersonDTO.builder()
        .id(person.getId())
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .build();
  }
}
