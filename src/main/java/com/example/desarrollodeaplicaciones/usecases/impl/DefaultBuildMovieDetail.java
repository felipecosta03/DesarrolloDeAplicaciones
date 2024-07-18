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
import com.example.desarrollodeaplicaciones.usecases.BuildImageUrl;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import com.mysql.cj.util.StringUtils;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieDetail implements BuildMovieDetail {

  private final BuildImageUrl buildImageUrl;

  public DefaultBuildMovieDetail(BuildImageUrl buildImageUrl) {
    this.buildImageUrl = buildImageUrl;
  }

  @Override
  public MovieDetail apply(MovieDetailDto movieDetailDto) {
    validateMovieDetailDto(movieDetailDto);

    if (!isNull(movieDetailDto.getBackdropPath()) && !movieDetailDto.getBackdropPath().isBlank()) {
      movieDetailDto.setBackdropPath(buildImageUrl.apply(movieDetailDto.getBackdropPath()));
    }
    if (!isNull(movieDetailDto.getPosterPath()) && !movieDetailDto.getPosterPath().isBlank()) {
      movieDetailDto.setPosterPath(buildImageUrl.apply(movieDetailDto.getPosterPath()));
    }

    return MovieDetail.builder()
        .id(movieDetailDto.getId())
        .title(movieDetailDto.getTitle())
        .overview(movieDetailDto.getOverview())
        .posterPath(movieDetailDto.getPosterPath())
        .backdropPath(movieDetailDto.getBackdropPath())
        .runtime(movieDetailDto.getRuntime())
        .releaseDate(movieDetailDto.getReleaseDate())
        .tagline(movieDetailDto.getTagline())
        .voteAverage(movieDetailDto.getVoteAverage())
        .voteCount(movieDetailDto.getVoteCount())
        .cast(
            Optional.ofNullable(movieDetailDto.getCast())
                .map(cast -> cast.stream().map(this::buildPeopleCrew).toList())
                .orElse(new ArrayList<>()))
        .director(buildPeopleCrew(movieDetailDto.getDirector()))
        .images(
            Optional.ofNullable(movieDetailDto.getImages())
                .map(images -> images.stream().map(this::buildImage).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(movieDetailDto.getVideos())
                .map(videos -> videos.stream().map(this::buildVideo).toList())
                .orElse(new ArrayList<>()))
        .genres(
            Optional.ofNullable(movieDetailDto.getGenres())
                .map(genres -> genres.stream().map(this::buildGenre).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  private Genre buildGenre(GenreDto genreDto) {
    if (isNull(genreDto)) {
      throw new FailedDependencyUseCaseException("genreDto cannot be null");
    }
    return Genre.builder().id(genreDto.getId()).name(genreDto.getName()).build();
  }

  private Image buildImage(MovieImageDto imageDto) {

    if (isNull(imageDto)) {
      throw new FailedDependencyUseCaseException("imageDto cannot be null");
    }
    if (!imageDto.getFilePath().isBlank()) {
      imageDto.setFilePath(buildImageUrl.apply(imageDto.getFilePath()));
    }
    return Image.builder().filePath(imageDto.getFilePath()).id(imageDto.getId()).build();
  }

  private Video buildVideo(MovieVideoDto videoDto) {
    if (isNull(videoDto)) {
      throw new FailedDependencyUseCaseException("videoDto cannot be null");
    }
    return Video.builder().key(videoDto.getKey()).id(videoDto.getId()).build();
  }

  private PeopleCast buildPeopleCrew(PeopleCastDto peopleCastDto) {
    if (isNull(peopleCastDto)) {
      return null;
    }

    if (!StringUtils.isNullOrEmpty(peopleCastDto.getProfilePath())) {
      peopleCastDto.setProfilePath(buildImageUrl.apply(peopleCastDto.getProfilePath()));
    }

    return PeopleCast.builder()
        .character(peopleCastDto.getCharacter())
        .name(peopleCastDto.getName())
        .profilePath(peopleCastDto.getProfilePath())
        .knownForDepartment(peopleCastDto.getKnownForDepartment())
        .id(peopleCastDto.getId())
        .build();
  }

  private PeopleCrew buildPeopleCrew(PeopleCrewDto peopleCrewDto) {
    if (isNull(peopleCrewDto)) {
      return null;
    }
    if (!StringUtils.isNullOrEmpty(peopleCrewDto.getProfilePath())) {
      peopleCrewDto.setProfilePath(buildImageUrl.apply(peopleCrewDto.getProfilePath()));
    }

    return PeopleCrew.builder()
        .name(peopleCrewDto.getName())
        .profilePath(peopleCrewDto.getProfilePath())
        .knownForDepartment(peopleCrewDto.getKnownForDepartment())
        .id(peopleCrewDto.getId())
        .job(peopleCrewDto.getJob())
        .build();
  }

  private void validateMovieDetailDto(MovieDetailDto movieDetailDto) {
    if (isNull(movieDetailDto)) {
      throw new BadRequestUseCaseException("movieDetailDto is required");
    }

    if (isNull(movieDetailDto.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
