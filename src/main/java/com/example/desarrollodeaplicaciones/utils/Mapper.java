package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.MediaDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.Media;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Person;
import com.example.desarrollodeaplicaciones.models.Rate;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(
                    movies ->
                        movies.stream().map(Mapper::movieToMovieDTO).collect(Collectors.toList()))
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
        .genres(
            Optional.ofNullable(movie.getGenres())
                .map(
                    genres ->
                        genres.stream().map(Mapper::genreToGenreDto).collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
        .images(
            Optional.ofNullable(movie.getImages())
                .map(
                    images ->
                        images.stream().map(Mapper::mediaToMediaDto).collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
        .trailer(mediaToMediaDto(movie.getTrailer()))
        .releaseDate(movie.getReleaseDate())
        .duration(movie.getDuration())
        .director(personToPersonDto(movie.getDirector()))
        .rateAverage(movie.getRateAverage())
        .rates(movie.getRates().stream().map(Mapper::rateToRateDto).collect(Collectors.toList()))
        .actors(
            Optional.ofNullable(movie.getActors())
                .map(
                    actors ->
                        actors.stream().map(Mapper::personToPersonDto).collect(Collectors.toList()))
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

  public static Genre genreDtoToGenre(GenreDTO genreDto) {
    return Genre.builder().id(genreDto.getId()).name(genreDto.getName()).build();
  }

  public static GenreDTO genreToGenreDto(Genre genre) {
    return GenreDTO.builder().id(genre.getId()).name(genre.getName()).build();
  }

  public static Movie movieDtoToMovie(MovieDTO movieDto) {
    return Movie.builder()
        .id(movieDto.getId())
        .title(movieDto.getTitle())
        .subtitle(movieDto.getSubtitle())
        .synapsis(movieDto.getSynapsis())
        .genres(
            Optional.ofNullable(movieDto.getGenres())
                .map(
                    genres ->
                        genres.stream().map(Mapper::genreDtoToGenre).collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
        .images(
            Optional.ofNullable(movieDto.getImages())
                .map(
                    images ->
                        images.stream().map(Mapper::mediaDtoToMedia).collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
        .trailer(mediaDtoToMedia(movieDto.getTrailer()))
        .releaseDate(movieDto.getReleaseDate())
        .duration(movieDto.getDuration())
        .director(personDtoToPerson(movieDto.getDirector()))
        .actors(
            Optional.ofNullable(movieDto.getActors())
                .map(
                    actors ->
                        actors.stream().map(Mapper::personDtoToPerson).collect(Collectors.toList()))
                .orElse(new ArrayList<>()))
        .build();
  }

  public static RateDTO rateToRateDto(Rate rate) {
    return RateDTO.builder().userId(rate.getUser().getId()).score(rate.getScore()).build();
  }

  public static Person personDtoToPerson(PersonDTO personDto) {
    return Person.builder().id(personDto.getId()).fullName(personDto.getFullName()).build();
  }

  public static PersonDTO personToPersonDto(Person person) {
    if (person == null) return null;
    return PersonDTO.builder().id(person.getId()).fullName(person.getFullName()).build();
  }


}
