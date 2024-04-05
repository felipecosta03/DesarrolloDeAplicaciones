package com.example.desarrollodeaplicaciones.dtos.moviesapi.response;

import com.example.desarrollodeaplicaciones.dtos.moviesapi.MovieSimpleApiDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDiscoverMoviesApiDTO {
    private List<MovieSimpleApiDTO> results;
}
