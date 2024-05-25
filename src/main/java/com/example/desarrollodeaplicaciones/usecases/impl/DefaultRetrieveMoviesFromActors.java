package com.example.desarrollodeaplicaciones.usecases.impl;

import static java.util.Objects.isNull;

import com.example.desarrollodeaplicaciones.dtos.ActorDto;
import com.example.desarrollodeaplicaciones.dtos.MovieSimpleDto;
import com.example.desarrollodeaplicaciones.exceptions.usecases.BadRequestUseCaseException;
import com.example.desarrollodeaplicaciones.usecases.RetrieveActorsByName;
import com.example.desarrollodeaplicaciones.usecases.RetrieveMoviesFromActors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DefaultRetrieveMoviesFromActors implements RetrieveMoviesFromActors {

  private final RetrieveActorsByName retrieveActorsByName;

  public DefaultRetrieveMoviesFromActors(RetrieveActorsByName retrieveActorsByName) {
    this.retrieveActorsByName = retrieveActorsByName;
  }

  @Override
  public Optional<List<MovieSimpleDto>> apply(Model model) {
    validateModel(model);
    // Retrieve actors by their names
    Optional<List<ActorDto>> actors =
        retrieveActorsByName.apply(
            RetrieveActorsByName.Model.builder().actorName(model.getActorName()).build());
    // Get movies from the actors retrieved
    List<MovieSimpleDto> movies = new ArrayList<>();

    actors.ifPresent(
        actorsDto -> actorsDto.forEach(actorDto -> movies.addAll(actorDto.getKnownFor())));
    if (movies.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(movies);
  }

  private void validateModel(Model model) {
    if (isNull(model)) {
      throw new BadRequestUseCaseException("Model is required");
    }
    if (model.getActorName().isEmpty()) {
      throw new BadRequestUseCaseException("Actor name is required");
    }
  }
}
