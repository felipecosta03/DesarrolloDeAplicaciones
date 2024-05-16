package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.Genre;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface SaveGenres extends Repository<Genre, Integer> {
  void save(List<Genre> genres);
}
