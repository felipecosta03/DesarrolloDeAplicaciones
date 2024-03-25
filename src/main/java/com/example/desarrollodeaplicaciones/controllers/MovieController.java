package com.example.desarrollodeaplicaciones.controllers;

import com.example.desarrollodeaplicaciones.dtos.MovieDTO;
import com.example.desarrollodeaplicaciones.services.IMovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/movies")
public class MovieController {

	private IMovieService movieServices;


	public MovieController(IMovieService movieController) {
		this.movieServices = movieController;
	}


	@PostMapping
	public ResponseEntity<MovieDTO> add(@RequestBody @Valid MovieDTO movie) {
		return ResponseEntity.status(200).body(movieServices.add(movie));
	}

	@GetMapping
	public ResponseEntity<List<MovieDTO>> findAll(){
		return ResponseEntity.status(200).body(movieServices.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(200).body(movieServices.findById(id));
	}

    @GetMapping("/hello")
    public String sayHello(@NotBlank(message = "Name no puede ver blanco") @RequestParam String name) {
        return "Hello, " + name + "!";
    }


}
