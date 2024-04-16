package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieDetailDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.exceptions.InvalidOrderParamException;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.Rate;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import com.example.desarrollodeaplicaciones.models.moviesapi.MovieSimple;
import com.example.desarrollodeaplicaciones.repositories.IMovieDetailPageableRepository;
import com.example.desarrollodeaplicaciones.repositories.IMovieDetailRepository;
import com.example.desarrollodeaplicaciones.repositories.IRateRepository;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService implements IMovieService {

  private static final String ORDER_ASC = "asc";
  private static final String ORDER_DESC = "desc";
  private static final String RELEASE_DATE = "releaseDate";
  private static final String QUALIFICATION = "voteAverage";
  private final IUserRepository userRepository;
  private final IMovieDetailPageableRepository moviePageableRepository;

  private final IMovieDetailRepository movieDetailRepository;
  private final MoviesApiService moviesApiService;
  private final IRateRepository rateRepository;

  public MovieService(
      IFilesStorage filesStorage,
      IUserRepository userRepository,
      IMovieDetailPageableRepository moviePageableRepository,
      IMovieDetailRepository movieDetailRepository,
      MoviesApiService moviesApiService,
      IRateRepository rateRepository) {
    this.userRepository = userRepository;
    this.moviePageableRepository = moviePageableRepository;
    this.movieDetailRepository = movieDetailRepository;
    this.moviesApiService = moviesApiService;
    this.rateRepository = rateRepository;
  }

  public List<MovieSimpleDTO> getAll(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> genre,
      Optional<Integer> page) {

    try {
       List<MovieSimple> movies =
          moviesApiService.getMoviesByPage(page.orElse(1), dateOrder, qualificationOrder, genre);
      return movies.stream().map(Mapper::movieSimpleApiDTOToMovieDTO).toList();
    } catch (Exception e) {
      log.info(e.getMessage());
      Sort sort = getSort(dateOrder, qualificationOrder);
      PageRequest pageRequest = getPageRequest(page, sort);
      return genre
          .map(
              genreData ->
                  moviePageableRepository.findAllByGenre(pageRequest, genreData).stream()
                      .map(Mapper::movieDetailToMovieSimpleDto)
                      .toList())
          .orElseGet(
              () ->
                  moviePageableRepository.findAll(pageRequest).stream()
                      .map(Mapper::movieDetailToMovieSimpleDto)
                      .toList());
    }
  }

  @Override
  public List<MovieSimpleDTO> getAllByTitleOrActor(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> value,
      Optional<Integer> page) {
    Sort sort = getSort(dateOrder, qualificationOrder);
    PageRequest pageRequest = getPageRequest(page, sort);
    return moviePageableRepository.findAllByTitleOrActor(pageRequest, value.orElse("")).stream()
        .map(Mapper::movieDetailToMovieSimpleDto)
        .toList();
  }



  @Override
  public StatusDTO updateRate(Long movieId, RateDTO rate) {
    return null;//TODO
  }

  @Override
  public StatusDTO deleteRate(Long movieId, Long userId) {
    return null;//TODO
  }

  @Override
  public MovieSimpleDTO getMovieSimpleById(Long movieId) {
    MovieDetail movieDetail;
    try{
      movieDetail = getMovieDetailById(movieId);
    } catch (MovieNotFoundException e) {
      log.warn("Movie not found in database");
      movieDetail = moviesApiService.getMovieDetailById(movieId);
      log.info("Retrieving movie from external API");
      moviesApiService.saveMovieDetailAsync(movieDetail);
    }
    return Mapper.movieDetailToMovieSimpleDto(movieDetail);
  }

  @Override
  public boolean existsMovie(Long movieId) {
    if(movieDetailRepository.existsById(movieId)){
        return true;
    }
    return moviesApiService.existsMovie(movieId);
  }

  public MovieDetailDTO findById(Long id) {
    MovieDetail movieDetail;
    try {
      movieDetail = getMovieDetailById(id);
    } catch (MovieNotFoundException e) {
      log.warn("Movie not found in database");
      movieDetail = moviesApiService.getMovieDetailById(id);
      log.info("Retrieving movie from external API");
      moviesApiService.saveMovieDetailAsync(movieDetail);
    }
    return Mapper.movieDetailToMovieDetailDto(movieDetail);
  }

  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  private PageRequest getPageRequest(Optional<Integer> page, Sort sort) {
    return PageRequest.of(page.orElse(0), 10, sort);
  }

  private Sort getSort(Optional<String> dateOrder, Optional<String> qualificationOrder) {
    Sort sort = Sort.by(Sort.Order.desc(RELEASE_DATE));
    if (dateOrder.isPresent()) {
      if (dateOrder.get().equalsIgnoreCase(ORDER_ASC)) {
        sort = Sort.by(Sort.Order.asc(RELEASE_DATE));
      } else if (dateOrder.get().equalsIgnoreCase(ORDER_DESC)) {
        sort = Sort.by(Sort.Order.desc(RELEASE_DATE));
      } else {
        throw new InvalidOrderParamException(RELEASE_DATE);
      }
    }

    if (qualificationOrder.isPresent()) {
      if (qualificationOrder.get().equalsIgnoreCase(ORDER_ASC)) {
        sort = sort.and(Sort.by(Sort.Order.asc(QUALIFICATION)));
      } else if (qualificationOrder.get().equalsIgnoreCase(ORDER_DESC)) {
        sort = sort.and(Sort.by(Sort.Order.desc(QUALIFICATION)));
      } else {
        throw new InvalidOrderParamException(QUALIFICATION);
      }
    }

    return sort;
  }

  private MovieDetail getMovieDetailById(Long id) {
    return movieDetailRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }
}
