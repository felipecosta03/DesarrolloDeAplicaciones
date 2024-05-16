package com.example.desarrollodeaplicaciones.router;

import com.example.desarrollodeaplicaciones.dtos.VoteDto;
import com.example.desarrollodeaplicaciones.usecases.AddVoteToMovie;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddVoteToMovieRouter extends MovieRouter {

  private final AddVoteToMovie addVoteToMovie;

  public AddVoteToMovieRouter(AddVoteToMovie addVoteToMovie) {
    this.addVoteToMovie = addVoteToMovie;
  }

  @PutMapping("/{movieId}/vote")
  public ResponseEntity<Void> put(
      @PathVariable("movieId") Long movieId, @Valid @RequestBody VoteDto voteDto) {
    addVoteToMovie.accept(
        AddVoteToMovie.Model.builder()
            .movieId(movieId)
            .userId(voteDto.getUserId())
            .score(voteDto.getScore())
            .build());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
