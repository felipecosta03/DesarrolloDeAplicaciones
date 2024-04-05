package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieDetailApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieSimpleApiDTO;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MoviesApiService {
  private final MoviesApiRepositoryImpl moviesApiRepository;

  public MoviesApiService(MoviesApiRepositoryImpl moviesApiRepository) {
    this.moviesApiRepository = moviesApiRepository;
  }

  public List<MovieSimpleApiDTO> getMoviesByPage(Integer page) {
    List<MovieSimpleApiDTO> movies = moviesApiRepository.getMoviesByPage(page).getResults();
    movies.forEach(
        movie ->
            movie.setPosterPath(
                String.format("https://image.tmdb.org/t/p/w500%s", movie.getPosterPath())));
    return movies;
  }

  public MovieDetailApiDTO getMovieDetailById(Integer id) {
    MovieDetailApiDTO movieDetail = moviesApiRepository.getMovieById(id);
    movieDetail.setPosterPath(
        String.format("https://image.tmdb.org/t/p/w500%s", movieDetail.getPosterPath()));
    movieDetail.setImages(moviesApiRepository.getMovieImages(id).getBackdrops());
    movieDetail.setVideos(moviesApiRepository.getMovieVideos(id).getResults());

    movieDetail
        .getImages()
        .forEach(
            movieImageApiDTO -> {
              movieImageApiDTO.setFilePath(
                  String.format(
                      "https://image.tmdb.org/t/p/w500/%s", movieImageApiDTO.getFilePath()));
            });

    movieDetail
        .getVideos()
        .forEach(
            movieVideoApiDTO -> {
              movieVideoApiDTO.setKey(
                  String.format("https://www.youtube.com/watch?v=%s", movieVideoApiDTO.getKey()));
            });
    return movieDetail;
  }
}
