package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieVideoDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCastDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDTO;
import com.example.desarrollodeaplicaciones.exceptions.BuildMovieDetailUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import com.example.desarrollodeaplicaciones.models.moviesapi.Video;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDTO;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieDetailDTO implements BuildMovieDetailDTO {
  @Override
  public MovieDetailDTO apply(Model model) {
    validateModel(model);
    return MovieDetailDTO.builder()
        .id(model.getMovieDetail().getId())
        .title(model.getMovieDetail().getTitle())
        .overview(model.getMovieDetail().getOverview())
        .posterPath(model.getMovieDetail().getPosterPath())
        .runtime(model.getMovieDetail().getRuntime())
        .releaseDate(model.getMovieDetail().getReleaseDate())
        .tagline(model.getMovieDetail().getTagline())
        .voteAverage(model.getMovieDetail().getVoteAverage())
        .voteCount(model.getMovieDetail().getVoteCount())
        .director(buildPeopleCast(model.getMovieDetail().getDirector()))
        .images(
            Optional.ofNullable(model.getMovieDetail().getImages())
                .map(images -> images.stream().map(this::buildImage).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(model.getMovieDetail().getVideos())
                .map(videos -> videos.stream().map(this::buildVideo).toList())
                .orElse(new ArrayList<>()))
        .genres(
            Optional.ofNullable(model.getMovieDetail().getGenres())
                .map(genres -> genres.stream().map(this::buildGenre).toList())
                .orElse(new ArrayList<>()))
        .cast(
            Optional.ofNullable(model.getMovieDetail().getCast())
                .map(cast -> cast.stream().map(this::buildPeopleCast).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  private GenreDTO buildGenre(Genre genre) {
    if (isNull(genre)) {
      throw new BuildMovieDetailUseCaseException("genre cannot be null");
    }
    return GenreDTO.builder().id(genre.getId()).name(genre.getName()).build();
  }

  private MovieImageDTO buildImage(Image image) {
    if (isNull(image)) {
      throw new BuildMovieDetailUseCaseException("image cannot be null");
    }
    return MovieImageDTO.builder().filePath(image.getFilePath()).id(image.getId()).build();
  }

  private MovieVideoDTO buildVideo(Video video) {
    if (isNull(video)) {
      throw new BuildMovieDetailUseCaseException("video cannot be null");
    }
    return MovieVideoDTO.builder().key(video.getKey()).id(video.getId()).build();
  }

  private PeopleCastDTO buildPeopleCast(PeopleCast peopleCast) {
    if (isNull(peopleCast)) {
      throw new BuildMovieDetailUseCaseException("peopleCast cannot be null");
    }
    return PeopleCastDTO.builder()
        .character(peopleCast.getCharacter())
        .name(peopleCast.getName())
        .profilePath(peopleCast.getProfilePath())
        .knownForDepartment(peopleCast.getKnownForDepartment())
        .id(peopleCast.getId())
        .build();
  }

  private PeopleCrewDTO buildPeopleCast(PeopleCrew peopleCrew) {
    if (isNull(peopleCrew)) {
      throw new BuildMovieDetailUseCaseException("peopleCrew cannot be null");
    }
    return PeopleCrewDTO.builder()
        .name(peopleCrew.getName())
        .profilePath(peopleCrew.getProfilePath())
        .knownForDepartment(peopleCrew.getKnownForDepartment())
        .id(peopleCrew.getId())
        .job(peopleCrew.getJob())
        .build();
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BuildMovieDetailUseCaseException("model is required");
    }

    if (isNull(model.getMovieDetail())) {
      throw new BuildMovieDetailUseCaseException("movieDetailDTO is required");
    }

    if (isNull(model.getMovieDetail().getId())) {
      throw new BuildMovieDetailUseCaseException("movie id is required");
    }
  }
}
