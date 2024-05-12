package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RetrieveActorsByNameDatabaseRepository
    extends PagingAndSortingRepository<Actor, Pageable> {
  Optional<List<Actor>> findAllByNameContaining(String actorName, Pageable pageable);
}
