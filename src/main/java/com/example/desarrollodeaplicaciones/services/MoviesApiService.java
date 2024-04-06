package com.example.desarrollodeaplicaciones.services;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieDetailApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieSimpleApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.response.ResponseCreditsApiDTO;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MoviesApiService {
  public static final String IMAGE_URL_BASE = "https://image.tmdb.org/t/p/w500%s";
  private final MoviesApiRepositoryImpl moviesApiRepository;

  public MoviesApiService(MoviesApiRepositoryImpl moviesApiRepository) {
    this.moviesApiRepository = moviesApiRepository;
  }

  public List<MovieSimpleApiDTO> getMoviesByPage(
      Integer page, Optional<String> dateOrder, Optional<String> qualificationOrder) {
    List<MovieSimpleApiDTO> movies =
        moviesApiRepository.getMoviesByPage(page, dateOrder, qualificationOrder).getResults();
    movies.forEach(
        movie -> {
          if (!isNull(movie.getPosterPath())) {
            movie.setPosterPath(String.format(IMAGE_URL_BASE, movie.getPosterPath()));
          }
        });
    return movies;
  }

  public MovieDetailApiDTO getMovieDetailById(Integer id) {
    MovieDetailApiDTO movieDetail = moviesApiRepository.getMovieById(id);
    ResponseCreditsApiDTO responseCredits = moviesApiRepository.getMovieCredits(id);

    movieDetail.setPosterPath(String.format(IMAGE_URL_BASE, movieDetail.getPosterPath()));
    movieDetail.setImages(moviesApiRepository.getMovieImages(id).getBackdrops());
    movieDetail.setVideos(moviesApiRepository.getMovieVideos(id).getResults());
    movieDetail.setCrew(responseCredits.getCrew());
    movieDetail.setCast(responseCredits.getCast());

    movieDetail
        .getCrew()
        .forEach(
            peopleCrewApiDTO -> {
              if (!isNull(peopleCrewApiDTO.getProfilePath())) {
                peopleCrewApiDTO.setProfilePath(
                    String.format(IMAGE_URL_BASE, peopleCrewApiDTO.getProfilePath()));
              }
            });
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
    return movieDetail;
  }
}
