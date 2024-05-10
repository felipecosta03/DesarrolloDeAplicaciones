package com.example.desarrollodeaplicaciones.utils;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.ImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieVideoDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCastDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.UserDTO;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.Rate;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import com.example.desarrollodeaplicaciones.models.moviesapi.Video;
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
        .image(imageToImage(user.getImage()))
        .active(user.isActive())
        .favoriteMovies(user.getFavoriteMovies())
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

  public static Image mediaDtoToMedia(ImageDTO imageDto) {
    if (imageDto == null) {
      return null;
    }
    return Image.builder().filePath(imageDto.getFilePath()).id(imageDto.getId()).build();
  }

  public static ImageDTO imageToImage(Image image) {
    if (image == null) {
      return null;
    }
return null;
  }

  public static Genre genreDtoToGenre(GenreDTO genreDto) {
    return Genre.builder().id(genreDto.getId()).name(genreDto.getName()).build();
  }

  public static GenreDTO genreToGenreDto(Genre genre) {
    return GenreDTO.builder().id(genre.getId()).name(genre.getName()).build();
  }

  public static RateDTO rateToRateDto(Rate rate) {
    return RateDTO.builder().userId(rate.getUser().getId()).score(rate.getScore()).build();
  }



  public static MovieSimpleDTO movieSimpleApiDTOToMovieDTO(MovieSimple movieSimple) {
    return MovieSimpleDTO.builder()
        .id(movieSimple.getId())
        .title(movieSimple.getTitle())
        .subtitle(movieSimple.getOriginalTitle())
        .synapsis(movieSimple.getOverview())
        .posterPath(movieSimple.getPosterPath())
        .build();
  }



  public static MovieDetailDTO movieDetailToMovieDetailDto(MovieDetail movieDetail) {
    return MovieDetailDTO.builder()
        .id(movieDetail.getId())
        .title(movieDetail.getTitle())
        .overview(movieDetail.getOverview())
        .posterPath(movieDetail.getPosterPath())
        .runtime(movieDetail.getRuntime())
        .releaseDate(movieDetail.getReleaseDate())
        .tagline(movieDetail.getTagline())
        .cast(
            Optional.ofNullable(movieDetail.getCast())
                .map(cast -> cast.stream().map(Mapper::peopleCastToPeopleCastDto).toList())
                .orElse(new ArrayList<>()))
        .director(peopleCrewToPeopleCrewDto(movieDetail.getDirector()))
        .genres(movieDetail.getGenres().stream().map(Mapper::genreToGenreDto).toList())
        .images(
            Optional.ofNullable(movieDetail.getImages())
                .map(images -> images.stream().map(Mapper::movieImageToMovieImageDto).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(movieDetail.getVideos())
                .map(videos -> videos.stream().map(Mapper::movieImageToMovieImageDto).toList())
                .orElse(new ArrayList<>()))
        .voteAverage(movieDetail.getVoteAverage())
        .voteCount(movieDetail.getVoteCount())
        .build();
  }

  public static PeopleCastDTO peopleCastToPeopleCastDto(PeopleCast peopleCast) {
    return PeopleCastDTO.builder()
        .profilePath(peopleCast.getProfilePath())
        .character(peopleCast.getCharacter())
        .name(peopleCast.getName())
        .knownForDepartment(peopleCast.getKnownForDepartment())
        .id(peopleCast.getId())
        .build();
  }

  public static MovieImageDTO movieImageToMovieImageDto(Image image) {
    return MovieImageDTO.builder()
        .filePath(image.getFilePath())
        .id(image.getId())
        .build();
  }

  public static MovieVideoDTO movieImageToMovieImageDto(Video video) {
    return MovieVideoDTO.builder().key(video.getKey()).id(video.getId()).build();
  }

  public static PeopleCrewDTO peopleCrewToPeopleCrewDto(PeopleCrew peopleCrew) {
    return PeopleCrewDTO.builder()
        .profilePath(peopleCrew.getProfilePath())
        .name(peopleCrew.getName())
        .knownForDepartment(peopleCrew.getKnownForDepartment())
        .id(peopleCrew.getId())
        .job(peopleCrew.getJob())
        .build();
  }

  public static MovieSimpleDTO movieDetailToMovieSimpleDto(MovieDetail movieDetail){
    return MovieSimpleDTO.builder()
        .id(movieDetail.getId())
        .title(movieDetail.getTitle())
        .subtitle(movieDetail.getOriginalTitle())
        .synapsis(movieDetail.getOverview())
        .posterPath(movieDetail.getPosterPath())
        .build();
  }
}
