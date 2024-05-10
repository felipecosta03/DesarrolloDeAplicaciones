package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieVideoDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCastDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDTO;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.FailedDependencyUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
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
  public MovieDetailDTO apply(MovieDetail movieDetail) {
    validateModel(movieDetail);
    return MovieDetailDTO.builder()
        .id(movieDetail.getId())
        .title(movieDetail.getTitle())
        .overview(movieDetail.getOverview())
        .posterPath(movieDetail.getPosterPath())
        .runtime(movieDetail.getRuntime())
        .releaseDate(movieDetail.getReleaseDate())
        .tagline(movieDetail.getTagline())
        .voteAverage(movieDetail.getVoteAverage())
        .voteCount(movieDetail.getVoteCount())
        .director(buildPeopleCrew(movieDetail.getDirector()))
        .images(
            Optional.ofNullable(movieDetail.getImages())
                .map(images -> images.stream().map(this::buildImage).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(movieDetail.getVideos())
                .map(videos -> videos.stream().map(this::buildVideo).toList())
                .orElse(new ArrayList<>()))
        .genres(
            Optional.ofNullable(movieDetail.getGenres())
                .map(genres -> genres.stream().map(this::buildGenre).toList())
                .orElse(new ArrayList<>()))
        .cast(
            Optional.ofNullable(movieDetail.getCast())
                .map(cast -> cast.stream().map(this::buildPeopleCrew).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  private GenreDTO buildGenre(Genre genre) {
    if (isNull(genre)) {
      throw new FailedDependencyUseCaseException("genre cannot be null");
    }
    return GenreDTO.builder().id(genre.getId()).name(genre.getName()).build();
  }

  private MovieImageDTO buildImage(Image image) {
    if (isNull(image)) {
      throw new FailedDependencyUseCaseException("image cannot be null");
    }
    return MovieImageDTO.builder().filePath(image.getFilePath()).id(image.getId()).build();
  }

  private MovieVideoDTO buildVideo(Video video) {
    if (isNull(video)) {
      throw new FailedDependencyUseCaseException("video cannot be null");
    }
    return MovieVideoDTO.builder().key(video.getKey()).id(video.getId()).build();
  }

  private PeopleCastDTO buildPeopleCrew(PeopleCast peopleCast) {
    if (isNull(peopleCast)) {
      throw new FailedDependencyUseCaseException("peopleCast cannot be null");
    }
    return PeopleCastDTO.builder()
        .character(peopleCast.getCharacter())
        .name(peopleCast.getName())
        .profilePath(peopleCast.getProfilePath())
        .knownForDepartment(peopleCast.getKnownForDepartment())
        .id(peopleCast.getId())
        .build();
  }

  private PeopleCrewDTO buildPeopleCrew(PeopleCrew peopleCrew) {
    if (isNull(peopleCrew)) {
      throw new FailedDependencyUseCaseException("peopleCrew cannot be null");
    }
    return PeopleCrewDTO.builder()
        .name(peopleCrew.getName())
        .profilePath(peopleCrew.getProfilePath())
        .knownForDepartment(peopleCrew.getKnownForDepartment())
        .id(peopleCrew.getId())
        .job(peopleCrew.getJob())
        .build();
  }

  private void validateModel(MovieDetail movieDetail) {
    if (isNull(movieDetail)) {
      throw new BadRequestUseCaseException("movieDetail is required");
    }

    if (isNull(movieDetail.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
