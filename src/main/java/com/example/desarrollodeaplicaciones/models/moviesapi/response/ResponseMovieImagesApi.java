package com.example.desarrollodeaplicaciones.models.moviesapi.response;

import com.example.desarrollodeaplicaciones.models.moviesapi.Image;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMovieImagesApi {
  private List<Image> backdrops;
}
