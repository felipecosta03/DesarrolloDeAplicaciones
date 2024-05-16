package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RetrieveGenresRepository extends Repository<Genre, Integer> {
  Optional<List<Genre>> findAll();
}
