package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.moviesapi.Actor;
import org.springframework.data.repository.Repository;

public interface SaveActorsRepository extends Repository<Actor, Long> {
  <S extends Actor> void saveAll(Iterable<S> entities);
}
