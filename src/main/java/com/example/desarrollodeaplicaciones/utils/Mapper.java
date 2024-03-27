package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.ActorDTO;
import com.example.desarrollodeaplicaciones.dtos.ImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.Actor;
import com.example.desarrollodeaplicaciones.models.Image;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.User;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
  private Mapper() {}

  public static UserDTO userToUserDto(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .lastName(user.getLastName())
        .nickName(user.getNickName())
        .email(user.getEmail())
        .image(imageToImageDto(user.getImage()))
        .active(user.isActive())
        .build();
  }

  public static User userDtoToUser(UserDTO userDto) {
    return User.builder()
        .id(userDto.getId())
        .name(userDto.getName())
        .lastName(userDto.getLastName())
        .nickName(userDto.getNickName())
        .email(userDto.getEmail())
        .image(imageDtoToImage(userDto.getImage()))
        .active(userDto.isActive())
        .build();
  }

  public static MovieDTO movieToMovieDTO(Movie movie) {
    return MovieDTO.builder()
        .title(movie.getTitle())
        .subtitle(movie.getSubtitle())
        .synapsis(movie.getSynapsis())
        .genre(movie.getGenre())
        .images(movie.getImages())
        .trailer(movie.getTrailer())
        .releasedDate(movie.getReleasedDate())
        .duration(movie.getDuration())
        .director(movie.getDirector())
        .actors(Mapper.actorsToActorsDTO(movie.getActors()))
        .build();
  }

  public static Image imageDtoToImage(ImageDTO imageDto) {
    if(imageDto== null){
      return new Image();
    }
    return Image.builder().url(imageDto.getUrl()).id(imageDto.getId()).build();
  }

  public static ImageDTO imageToImageDto(Image image) {
    if (image == null) {
      return new ImageDTO();
    }
    return ImageDTO.builder().url(image.getUrl()).id(image.getId()).build();
  }

  public static Movie movieDtoToMovie(MovieDTO movieDto) {
    return Movie.builder()
        .title(movieDto.getTitle())
        .subtitle(movieDto.getSubtitle())
        .synapsis(movieDto.getSynapsis())
        .genre(movieDto.getGenre())
        .images(movieDto.getImages())
        .trailer(movieDto.getTrailer())
        .releasedDate(movieDto.getReleasedDate())
        .duration(movieDto.getDuration())
        .director(movieDto.getDirector())
        .actors(Mapper.actorsDtoToActors(movieDto.getActors()))
        .build();
  }

  public static Actor actorDtoToActor(ActorDTO actorDto) {
    return Actor.builder()
        .firstName(actorDto.getFirstName())
        .lastName(actorDto.getLastName())
        .build();
  }

  public static ActorDTO actorToActorDTO(Actor actor) {
    return ActorDTO.builder().firstName(actor.getFirstName()).lastName(actor.getLastName()).build();
  }

  public static List<Actor> actorsDtoToActors(List<ActorDTO> actorsDto) {
    ArrayList<Actor> actors = new ArrayList<>();
    for (ActorDTO actorDto : actorsDto) {
      actors.add(
          Actor.builder()
              .firstName(actorDto.getFirstName())
              .lastName(actorDto.getLastName())
              .build());
    }
    return actors;
  }

  public static List<ActorDTO> actorsToActorsDTO(List<Actor> actors) {
    ArrayList<ActorDTO> actorsDto = new ArrayList<>();
    for (Actor actor : actors) {
      actorsDto.add(Mapper.actorToActorDTO(actor));
    }
    return actorsDto;
  }

  public static List<MovieDTO> listMoviesToArrayListMoviesDTO(List<Movie> moviesList) {
    ArrayList<MovieDTO> movies = new ArrayList<>();
    for (Movie movie : moviesList) {
      movies.add(Mapper.movieToMovieDTO(movie));
    }
    return movies;
  }
}
