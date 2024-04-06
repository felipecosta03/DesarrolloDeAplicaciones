package com.example.desarrollodeaplicaciones.services;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.models.moviesapi.response.ResponseCreditsApiDTO;
import com.example.desarrollodeaplicaciones.repositories.IMovieDetailRepository;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MoviesApiService {
  public static final String IMAGE_URL_BASE = "https://image.tmdb.org/t/p/w500%s";
  private final MoviesApiRepositoryImpl moviesApiRepository;
  private final IFilesStorage filesStorage;
  private final IMovieDetailRepository movieDetailRepository;

  public MoviesApiService(
      MoviesApiRepositoryImpl moviesApiRepository,
      IFilesStorage filesStorage,
      IMovieDetailRepository movieDetailRepository) {
    this.moviesApiRepository = moviesApiRepository;
    this.filesStorage = filesStorage;
    this.movieDetailRepository = movieDetailRepository;
  }

  public List<MovieSimple> getMoviesByPage(
      Integer page, Optional<String> dateOrder, Optional<String> qualificationOrder) {
    List<MovieSimple> movies =
        moviesApiRepository.getMoviesByPage(page, dateOrder, qualificationOrder).getResults();
    movies.forEach(
        movie -> {
          if (!isNull(movie.getPosterPath())) {
            movie.setPosterPath(String.format(IMAGE_URL_BASE, movie.getPosterPath()));
          }
        });
    return movies;
  }

  public MovieDetail getMovieDetailById(Long id) {
    MovieDetail movieDetail = moviesApiRepository.getMovieById(id);
    ResponseCreditsApiDTO responseCredits = moviesApiRepository.getMovieCredits(id);
    movieDetail.setPosterPath(String.format(IMAGE_URL_BASE, movieDetail.getPosterPath()));
    movieDetail.setImages(moviesApiRepository.getMovieImages(id).getBackdrops());
    movieDetail.setVideos(moviesApiRepository.getMovieVideos(id).getResults());
    movieDetail.setDirector(
        responseCredits.getCrew().stream()
            .filter(people -> people.getJob().equals("Director"))
            .findFirst()
            .orElse(null));

    if (!isNull(movieDetail.getDirector().getProfilePath()))
      movieDetail
          .getDirector()
          .setProfilePath(
              String.format(IMAGE_URL_BASE, movieDetail.getDirector().getProfilePath()));

    movieDetail.setCast(responseCredits.getCast().subList(0, 8));
    movieDetail
        .getCast()
        .forEach(
            peopleCastApiDTO -> {
              if (!isNull(peopleCastApiDTO.getProfilePath())) {
                peopleCastApiDTO.setProfilePath(
                    String.format(IMAGE_URL_BASE, peopleCastApiDTO.getProfilePath()));
              }
            });

    movieDetail
        .getImages()
        .forEach(
            movieImageApiDTO -> {
              if (!isNull(movieImageApiDTO.getFilePath())) {
                movieImageApiDTO.setFilePath(
                    String.format(IMAGE_URL_BASE, movieImageApiDTO.getFilePath()));
              }
            });

    movieDetail
        .getVideos()
        .forEach(
            movieVideoApiDTO -> {
              if (!isNull(movieVideoApiDTO.getKey()))
                movieVideoApiDTO.setKey(
                    String.format("https://www.youtube.com/watch?v=%s", movieVideoApiDTO.getKey()));
            });

    if (!isNull(movieDetail.getPosterPath())) {
      movieDetail.setPosterPath(String.format(IMAGE_URL_BASE, movieDetail.getPosterPath()));
    }

    return movieDetail;
  }

  @Async
  public void saveMovieDetailAsync(MovieDetail movieDetail) {
    log.info("Saving movie detail async");
    movieDetail
        .getImages()
        .forEach(
            movieImage -> {
              if (!isNull(movieImage.getFilePath())) {
                String url = filesStorage.uploadImage(movieImage.getFilePath());
                movieImage.setFilePath(url);
              }
            });
    movieDetail
        .getCast()
        .forEach(
            peopleCast -> {
              if (!isNull(peopleCast.getProfilePath())) {
                String url = filesStorage.uploadImage(peopleCast.getProfilePath());
                peopleCast.setProfilePath(url);
              }
            });
    if (!isNull(movieDetail.getDirector().getProfilePath())) {
      String url = filesStorage.uploadImage(movieDetail.getDirector().getProfilePath());
      movieDetail.getDirector().setProfilePath(url);
    }
    if(!isNull(movieDetail.getPosterPath())){
        String url = filesStorage.uploadImage(movieDetail.getPosterPath());
        movieDetail.setPosterPath(url);
    }
    movieDetailRepository.save(movieDetail);
  }
}
