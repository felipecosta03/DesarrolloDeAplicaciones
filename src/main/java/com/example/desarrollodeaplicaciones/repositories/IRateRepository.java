package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.Rate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRateRepository extends JpaRepository<Rate, Long> {

}
