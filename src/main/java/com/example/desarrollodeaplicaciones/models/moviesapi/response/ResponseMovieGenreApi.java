package com.example.desarrollodeaplicaciones.models.moviesapi.response;

import com.example.desarrollodeaplicaciones.dtos.GenreDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMovieGenreApi {
  private List<GenreDto> genres;
}
