package com.example.desarrollodeaplicaciones.dtos.moviesapi.response;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieImageApiDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMovieImagesApiDTO {
    private List<MovieImageApiDTO> backdrops;
}
