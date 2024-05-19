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
  public MovieDetail apply(MovieDetailDto movieDetailDTO) {
    validateMovieDetailDTO(movieDetailDTO);

    if (!movieDetailDTO.getBackdropPath().isBlank()) {
      movieDetailDTO.setBackdropPath(buildImageUrl.apply(movieDetailDTO.getBackdropPath()));
    }
    if (!movieDetailDTO.getPosterPath().isBlank()) {
      movieDetailDTO.setPosterPath(buildImageUrl.apply(movieDetailDTO.getPosterPath()));
    }

    return MovieDetail.builder()
        .id(movieDetailDTO.getId())
        .title(movieDetailDTO.getTitle())
        .overview(movieDetailDTO.getOverview())
        .posterPath(movieDetailDTO.getPosterPath())
        .backdropPath(movieDetailDTO.getBackdropPath())
        .runtime(movieDetailDTO.getRuntime())
        .releaseDate(movieDetailDTO.getReleaseDate())
        .tagline(movieDetailDTO.getTagline())
        .voteAverage(movieDetailDTO.getVoteAverage())
        .voteCount(movieDetailDTO.getVoteCount())
        .cast(
            Optional.ofNullable(movieDetailDTO.getCast())
                .map(cast -> cast.stream().map(this::buildPeopleCrew).toList())
                .orElse(new ArrayList<>()))
        .director(buildPeopleCrew(movieDetailDTO.getDirector()))
        .images(
            Optional.ofNullable(movieDetailDTO.getImages())
                .map(images -> images.stream().map(this::buildImage).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(movieDetailDTO.getVideos())
                .map(videos -> videos.stream().map(this::buildVideo).toList())
                .orElse(new ArrayList<>()))
        .genres(
            Optional.ofNullable(movieDetailDTO.getGenres())
                .map(genres -> genres.stream().map(this::buildGenre).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  private Genre buildGenre(GenreDto genreDTO) {
    if (isNull(genreDTO)) {
      throw new FailedDependencyUseCaseException("genreDTO cannot be null");
    }
    return Genre.builder().id(genreDTO.getId()).name(genreDTO.getName()).build();
  }

  private Image buildImage(MovieImageDto imageDTO) {

    if (isNull(imageDTO)) {
      throw new FailedDependencyUseCaseException("imageDTO cannot be null");
    }
    if (!imageDTO.getFilePath().isBlank()) {
      imageDTO.setFilePath(buildImageUrl.apply(imageDTO.getFilePath()));
    }
    return Image.builder().filePath(imageDTO.getFilePath()).id(imageDTO.getId()).build();
  }

  private Video buildVideo(MovieVideoDto videoDTO) {
    if (isNull(videoDTO)) {
      throw new FailedDependencyUseCaseException("videoDTO cannot be null");
    }
    return Video.builder().key(videoDTO.getKey()).id(videoDTO.getId()).build();
  }

  private PeopleCast buildPeopleCrew(PeopleCastDto peopleCastDTO) {
    if (isNull(peopleCastDTO)) {
      throw new FailedDependencyUseCaseException("peopleCastDTO cannot be null");
    }

    if (!StringUtils.isNullOrEmpty(peopleCastDTO.getProfilePath())) {
      peopleCastDTO.setProfilePath(buildImageUrl.apply(peopleCastDTO.getProfilePath()));
    }

    return PeopleCast.builder()
        .character(peopleCastDTO.getCharacter())
        .name(peopleCastDTO.getName())
        .profilePath(peopleCastDTO.getProfilePath())
        .knownForDepartment(peopleCastDTO.getKnownForDepartment())
        .id(peopleCastDTO.getId())
        .build();
  }

  private PeopleCrew buildPeopleCrew(PeopleCrewDto peopleCrewDTO) {
    if (isNull(peopleCrewDTO)) {
      throw new FailedDependencyUseCaseException("peopleCrewDTO cannot be null");
    }

    if (!StringUtils.isNullOrEmpty(peopleCrewDTO.getProfilePath())) {
      peopleCrewDTO.setProfilePath(buildImageUrl.apply(peopleCrewDTO.getProfilePath()));
    }

    return PeopleCrew.builder()
        .name(peopleCrewDTO.getName())
        .profilePath(peopleCrewDTO.getProfilePath())
        .knownForDepartment(peopleCrewDTO.getKnownForDepartment())
        .id(peopleCrewDTO.getId())
        .job(peopleCrewDTO.getJob())
        .build();
  }

  private void validateMovieDetailDTO(MovieDetailDto movieDetailDTO) {
    if (isNull(movieDetailDTO)) {
      throw new BadRequestUseCaseException("movieDetailDto is required");
    }

    if (isNull(movieDetailDTO.getId())) {
      throw new BadRequestUseCaseException("movie id is required");
    }
  }
}
