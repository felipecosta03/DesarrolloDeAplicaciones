package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IRateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT r FROM Movie m INNER JOIN m.rates r WHERE m.id=:movieId AND r.user.id=:userId")
    Optional<Rate> findByMovieIdAndUserId(Long movieId, Long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Movie m INNER JOIN m.rates r WHERE m.id=:movieId AND r.user.id=:userId")
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);
}
