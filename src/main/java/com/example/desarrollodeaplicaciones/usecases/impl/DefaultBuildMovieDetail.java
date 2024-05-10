package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.GenreDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieImageDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieVideoDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCastDTO;
import com.example.desarrollodeaplicaciones.dtos.PeopleCrewDTO;
import com.example.desarrollodeaplicaciones.exceptions.BuildMovieDetailUseCaseException;
import com.example.desarrollodeaplicaciones.models.Genre;
import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCast;
import com.example.desarrollodeaplicaciones.models.moviesapi.PeopleCrew;
import com.example.desarrollodeaplicaciones.models.moviesapi.Video;
import com.example.desarrollodeaplicaciones.usecases.BuildMovieDetail;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultBuildMovieDetail implements BuildMovieDetail {
  @Override
  public MovieDetail apply(Model model) {
    validateModel(model);
    return MovieDetail.builder()
        .id(model.getMovieDetailDTO().getId())
        .title(model.getMovieDetailDTO().getTitle())
        .overview(model.getMovieDetailDTO().getOverview())
        .posterPath(model.getMovieDetailDTO().getPosterPath())
        .runtime(model.getMovieDetailDTO().getRuntime())
        .releaseDate(model.getMovieDetailDTO().getReleaseDate())
        .tagline(model.getMovieDetailDTO().getTagline())
        .voteAverage(model.getMovieDetailDTO().getVoteAverage())
        .voteCount(model.getMovieDetailDTO().getVoteCount())
        .cast(
            Optional.ofNullable(model.getMovieDetailDTO().getCast())
                .map(cast -> cast.stream().map(this::buildPeopleCast).toList())
                .orElse(new ArrayList<>()))
        .director(buildPeopleCast(model.getMovieDetailDTO().getDirector()))
        .images(
            Optional.ofNullable(model.getMovieDetailDTO().getImages())
                .map(images -> images.stream().map(this::buildImage).toList())
                .orElse(new ArrayList<>()))
        .videos(
            Optional.ofNullable(model.getMovieDetailDTO().getVideos())
                .map(videos -> videos.stream().map(this::buildVideo).toList())
                .orElse(new ArrayList<>()))
        .genres(
            Optional.ofNullable(model.getMovieDetailDTO().getGenres())
                .map(genres -> genres.stream().map(this::buildGenre).toList())
                .orElse(new ArrayList<>()))
        .build();
  }

  private Genre buildGenre(GenreDTO genreDTO) {
    if (isNull(genreDTO)) {
      throw new BuildMovieDetailUseCaseException("genreDTO cannot be null");
    }
    return Genre.builder().id(genreDTO.getId()).name(genreDTO.getName()).build();
  }

  private Image buildImage(MovieImageDTO imageDTO) {
    if (isNull(imageDTO)) {
      throw new BuildMovieDetailUseCaseException("imageDTO cannot be null");
    }
    return Image.builder().filePath(imageDTO.getFilePath()).id(imageDTO.getId()).build();
  }

  private Video buildVideo(MovieVideoDTO videoDTO) {
    if (isNull(videoDTO)) {
      throw new BuildMovieDetailUseCaseException("videoDTO cannot be null");
    }
    return Video.builder().key(videoDTO.getKey()).id(videoDTO.getId()).build();
  }

  private PeopleCast buildPeopleCast(PeopleCastDTO peopleCastDTO) {
    if (isNull(peopleCastDTO)) {
      throw new BuildMovieDetailUseCaseException("peopleCastDTO cannot be null");
    }
    return PeopleCast.builder()
        .character(peopleCastDTO.getCharacter())
        .name(peopleCastDTO.getName())
        .profilePath(peopleCastDTO.getProfilePath())
        .knownForDepartment(peopleCastDTO.getKnownForDepartment())
        .id(peopleCastDTO.getId())
        .build();
  }

  private PeopleCrew buildPeopleCast(PeopleCrewDTO peopleCrewDTO) {
    if (isNull(peopleCrewDTO)) {
      throw new BuildMovieDetailUseCaseException("peopleCrewDTO cannot be null");
    }
    return PeopleCrew.builder()
        .name(peopleCrewDTO.getName())
        .profilePath(peopleCrewDTO.getProfilePath())
        .knownForDepartment(peopleCrewDTO.getKnownForDepartment())
        .id(peopleCrewDTO.getId())
        .job(peopleCrewDTO.getJob())
        .build();
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BuildMovieDetailUseCaseException("model is required");
    }

    if (isNull(model.getMovieDetailDTO())) {
      throw new BuildMovieDetailUseCaseException("movieDetailDTO is required");
    }

    if (isNull(model.getMovieDetailDTO().getId())) {
      throw new BuildMovieDetailUseCaseException("movie id is required");
    }
  }
}
