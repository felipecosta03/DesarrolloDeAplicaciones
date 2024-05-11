package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDto;
import com.example.desarrollodeaplicaciones.dtos.MovieImageDto;
import com.example.desarrollodeaplicaciones.dtos.MovieVideoDto;
import com.example.desarrollodeaplicaciones.dtos.PeopleCastDto;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.exceptions.usecases.FailedDependencyUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import com.example.desarrollodeaplicaciones.models.moviesapi.Video;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetailDto;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieDetailDto implements BuildMovieDetailDto {
  @Override
  public MovieDetailDto apply(MovieDetail movieDetail) {
    validateMovieDetail(movieDetail);
    return MovieDetailDto.builder()
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

  private GenreDto buildGenre(Genre genre) {
    if (isNull(genre)) {
      throw new FailedDependencyUseCaseException("genre cannot be null");
    }
    return GenreDto.builder().id(genre.getId()).name(genre.getName()).build();
  }

  private MovieImageDto buildImage(Image image) {
    if (isNull(image)) {
      throw new FailedDependencyUseCaseException("image cannot be null");
    }
    return MovieImageDto.builder().filePath(image.getFilePath()).id(image.getId()).build();
  }

  private MovieVideoDto buildVideo(Video video) {
    if (isNull(video)) {
      throw new FailedDependencyUseCaseException("video cannot be null");
    }
    return MovieVideoDto.builder().key(video.getKey()).id(video.getId()).build();
  }

  private PeopleCastDto buildPeopleCrew(PeopleCast peopleCast) {
    if (isNull(peopleCast)) {
      throw new FailedDependencyUseCaseException("peopleCast cannot be null");
    }
    return PeopleCastDto.builder()
        .character(peopleCast.getCharacter())
        .name(peopleCast.getName())
        .profilePath(peopleCast.getProfilePath())
        .knownForDepartment(peopleCast.getKnownForDepartment())
        .id(peopleCast.getId())
        .build();
  }

  private PeopleCrewDto buildPeopleCrew(PeopleCrew peopleCrew) {
    if (isNull(peopleCrew)) {
      throw new FailedDependencyUseCaseException("peopleCrew cannot be null");
    }
    return PeopleCrewDto.builder()
        .name(peopleCrew.getName())
        .profilePath(peopleCrew.getProfilePath())
        .knownForDepartment(peopleCrew.getKnownForDepartment())
        .id(peopleCrew.getId())
        .job(peopleCrew.getJob())
        .build();
  }

  private void validateMovieDetail(MovieDetail movieDetail) {
    if (isNull(movieDetail)) {
      throw new BadRequestUseCaseException("movieDetail is required");
    }

    if (isNull(movieDetail.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
