package com.example.desarrollodeaplicaciones.services;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDTO;
import com.example.desarrollodeaplicaciones.dtos.RateDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieDetailApiDTO;
import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieSimpleApiDTO;
import com.example.desarrollodeaplicaciones.exceptions.InvalidOrderParamException;
import com.example.desarrollodeaplicaciones.exceptions.MovieNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.RateAlreadyExistsException;
import com.example.desarrollodeaplicaciones.exceptions.RateNotFoundException;
import com.example.desarrollodeaplicaciones.exceptions.UserNotFoundException;
import com.example.desarrollodeaplicaciones.models.Movie;
import com.example.desarrollodeaplicaciones.models.Rate;
import com.example.desarrollodeaplicaciones.models.User;
import com.example.desarrollodeaplicaciones.repositories.IMoviePageableRepository;
import com.example.desarrollodeaplicaciones.repositories.IMovieRepository;
import com.example.desarrollodeaplicaciones.repositories.IPersonRepository;
import com.example.desarrollodeaplicaciones.repositories.IRateRepository;
import com.example.desarrollodeaplicaciones.repositories.IUserRepository;
import com.example.desarrollodeaplicaciones.repositories.MoviesApiRepositoryImpl;
import com.example.desarrollodeaplicaciones.utils.Mapper;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MovieService implements IMovieService {

  private static final String ORDER_ASC = "asc";
  private static final String ORDER_DESC = "desc";
  private static final String RELEASE_DATE = "releaseDate";
  private static final String QUALIFICATION = "rateAverage";
  private final IMovieRepository movieRepository;
  private final IFilesStorage filesStorage;
  private final IPersonRepository personRepository;
  private final IUserRepository userRepository;
  private final IMoviePageableRepository moviePageableRepository;

  private final MoviesApiService moviesApiService;
  private final IRateRepository rateRepository;

  public MovieService(
      IMovieRepository movieRepository,
      IFilesStorage filesStorage,
      IPersonRepository personRepository,
      IUserRepository userRepository,
      IMoviePageableRepository moviePageableRepository,
      MoviesApiRepositoryImpl moviesApiRepository,
      MoviesApiService moviesApiService,
      IRateRepository rateRepository) {
    this.movieRepository = movieRepository;
    this.filesStorage = filesStorage;
    this.personRepository = personRepository;
    this.userRepository = userRepository;
    this.moviePageableRepository = moviePageableRepository;
    this.moviesApiService = moviesApiService;
    this.rateRepository = rateRepository;
  }

  public List<MovieSimpleDTO> getAll(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> genre,
      Optional<Integer> page) {

    try {
      List<MovieSimpleApiDTO> movies =
          moviesApiService.getMoviesByPage(page.orElse(1), dateOrder, qualificationOrder);
      return movies.stream().map(Mapper::movieSimpleApiDTOToMovieDTO).toList();
    } catch (Exception e) {
      Sort sort = getSort(dateOrder, qualificationOrder);
      PageRequest pageRequest = getPageRequest(page, sort);
      return genre
          .map(
              genreName ->
                  moviePageableRepository.findAllByGenre(pageRequest, genreName).stream()
                      .map(Mapper::movieToMovieSimpleDto)
                      .toList())
          .orElseGet(
              () ->
                  moviePageableRepository.findAll(pageRequest).stream()
                      .map(Mapper::movieToMovieSimpleDto)
                      .toList());
    }
  }

  @Override
  public List<MovieDTO> getAllByTitleOrActor(
      Optional<String> dateOrder,
      Optional<String> qualificationOrder,
      Optional<String> value,
      Optional<Integer> page) {
    Sort sort = getSort(dateOrder, qualificationOrder);
    PageRequest pageRequest = getPageRequest(page, sort);
    return moviePageableRepository.findAllByTitleOrActor(pageRequest, value.orElse("")).stream()
        .map(Mapper::movieToMovieDTO)
        .toList();
  }

  @Override
  public StatusDTO addRate(Long movieId, RateDTO rate) {
    Movie movie = getMovieById(movieId);
    if (rateRepository.existsByMovieIdAndUserId(movieId, rate.getUserId())) {
      throw new RateAlreadyExistsException(movieId, rate.getUserId());
    }
    User user = getUserById(rate.getUserId());
    Rate newRate = Rate.builder().score(rate.getScore()).user(user).build();
    movie.getRates().add(newRate);
    movie.setRateAverage(calculateRateAverage(movie.getRates()));
    movieRepository.save(movie);
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO updateRate(Long movieId, RateDTO rate) {
    Rate rateToUpdate =
        rateRepository
            .findByMovieIdAndUserId(movieId, rate.getUserId())
            .orElseThrow(RateNotFoundException::new);
    rateToUpdate.setScore(rate.getScore());
    rateRepository.save(rateToUpdate);
    return StatusDTO.builder().status(200).build();
  }

  @Override
  public StatusDTO deleteRate(Long movieId, Long userId) {
    Movie movie = getMovieById(movieId);
    boolean rateWasRemoved = movie.getRates().removeIf(q -> q.getUser().getId().equals(userId));
    if (!rateWasRemoved) {
      throw new RateNotFoundException();
    }
    movie.setRateAverage(calculateRateAverage(movie.getRates()));
    movieRepository.save(movie);
    return StatusDTO.builder().status(200).build();
  }

  public MovieDTO findById(Long id) {
    Movie movie;
    try {
      movie = getMovieById(id);
    } catch (MovieNotFoundException e) {
      MovieDetailApiDTO movieDetailApi = moviesApiService.getMovieDetailById(id);
      movie = Mapper.movieDetailApiDtoToMovie(movieDetailApi);
      movie.setSynapsis(movie.getSynapsis().substring(0,50));
      movieRepository.save(movie);
    }
    return Mapper.movieToMovieDTO(movie);
  }

  private User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  private Double calculateRateAverage(List<Rate> rates) {
    return rates.stream().mapToDouble(Rate::getScore).average().orElse(0);
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

  private Movie getMovieById(Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }
}
