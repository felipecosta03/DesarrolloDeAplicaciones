package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.BuildMoviesComparator;
import com.example.desarrollodeaplicaciones.usecases.MergeMovies;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesBySearch;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesByTitle;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesFromActors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultRetrieveMoviesBySearch implements RetrieveMoviesBySearch {

    private final RetrieveMoviesByTitle retrieveMoviesByTitle;
    private final RetrieveMoviesFromActors retrieveMoviesFromActors;
    private final MergeMovies mergeMovies;
    private final BuildMoviesComparator buildMoviesComparator;

    public DefaultRetrieveMoviesBySearch(
            RetrieveMoviesByTitle retrieveMoviesByTitle,
            RetrieveMoviesFromActors retrieveMoviesFromActors,
            MergeMovies mergeMovies,
            BuildMoviesComparator buildMoviesComparator) {
        this.retrieveMoviesByTitle = retrieveMoviesByTitle;
        this.retrieveMoviesFromActors = retrieveMoviesFromActors;
        this.mergeMovies = mergeMovies;
        this.buildMoviesComparator = buildMoviesComparator;
    }

    @Override
    public Optional<List<MovieSimpleDto>> apply(Model model) {
        validateModel(model);
        // Retrieve movies by actors from the API or database if an api error occurs
        log.info("Retrieving movies by actors name: {}", model.getValue());
        final Optional<List<MovieSimpleDto>> moviesFromActors =
                retrieveMoviesFromActors.apply(
                        RetrieveMoviesFromActors.Model.builder()
                                .actorName(model.getValue())
                                .size(model.getSize())
                                .page(model.getPage())
                                .dateOrder(model.getDateOrder())
                                .qualificationOrder(model.getQualificationOrder())
                                .build());

        // Retrieve movies by a search from the API or database if an api error occurs
        log.info("Retrieving movies by title value: {}", model.getValue());
        final Optional<List<MovieSimpleDto>> moviesByTitle =
                retrieveMoviesByTitle.apply(
                        RetrieveMoviesByTitle.Model.builder()
                                .title(model.getValue())
                                .size(model.getSize())
                                .page(model.getPage())
                                .dateOrder(model.getDateOrder())
                                .qualificationOrder(model.getQualificationOrder())
                                .build());

        // Merge movies of actors with the movies retrieved by the search
        Comparator<MovieSimpleDto> comparator =
                buildMoviesComparator.apply(
                        BuildMoviesComparator.Model.builder()
                                .dateOrder(model.getDateOrder())
                                .qualificationOrder(model.getQualificationOrder())
                                .build());
        final List<MovieSimpleDto> movies =
                mergeMovies.apply(
                        moviesByTitle.orElse(Collections.emptyList()),
                        moviesFromActors.orElse(Collections.emptyList()));
        if (!Strings.isNullOrEmpty(model.getQualificationOrder())) cleanDate(movies);
        if (!isNull(comparator)) movies.sort(comparator);

        if (movies.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(movies);
    }

    private void cleanDate(List<MovieSimpleDto> movies) {
        movies.forEach(movie -> {
            if (!Strings.isNullOrEmpty(movie.getReleaseDate())) {
                movie.setReleaseDate(movie.getReleaseDate().substring(0, 4));
            }
            log.info(movie.getReleaseDate());
        });
    }

    private void validateModel(Model model) {
        if (isNull(model)) {
            throw new BadRequestUseCaseException("Model is required");
        }
        if (model.getValue().isBlank()) {
            throw new BadRequestUseCaseException("Value is required");
        }
    }
}
