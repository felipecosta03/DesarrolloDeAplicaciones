package com.example.desarrollodeaplicaciones.models.moviesapi.response;

import com.example.desarrollodeaplicaciones.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGenreApi {
    private List<Genre> genres;
}
