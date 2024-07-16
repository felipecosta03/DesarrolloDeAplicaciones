package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import com.example.desarrollodeaplicaciones.usecases.RetrieveGenres;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveGenresRouter extends MovieRouter {
  private final RetrieveGenres retrieveGenres;

  public RetrieveGenresRouter(RetrieveGenres retrieveGenres) {
    this.retrieveGenres = retrieveGenres;
  }

  @GetMapping("/genres")
  public List<GenreDto> getGenres() {
    return retrieveGenres.get();
  }
}
