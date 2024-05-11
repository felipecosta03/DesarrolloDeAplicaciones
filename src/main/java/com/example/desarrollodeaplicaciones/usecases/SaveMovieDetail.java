package com.example.desarrollodeaplicaciones.usecases;

import com.example.desarrollodeaplicaciones.models.moviesapi.MovieDetail;
import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;

public interface SaveMovieDetail extends Consumer<MovieDetail> {
}
