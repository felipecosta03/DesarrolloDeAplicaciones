package com.example.desarrollodeaplicaciones.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.services.IMovieService;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
	
	private IMovieService movieServices;
	
	
	public MovieController(IMovieService movieController) {
		this.movieServices = movieController;
	}
	
	
	@PostMapping
	public ResponseEntity<MovieDTO> add(@RequestBody MovieDTO movie) {
		return ResponseEntity.status(200).body(movieServices.add(movie));
	}
	
	@GetMapping
	public ResponseEntity<List<MovieDTO>> findAll(){
		return ResponseEntity.status(200).body(movieServices.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable String id) {
		return ResponseEntity.status(200).body(movieServices.findById(id));
	}

	
}
