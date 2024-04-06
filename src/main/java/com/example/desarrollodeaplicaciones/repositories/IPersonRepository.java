package com.example.desarrollodeaplicaciones.repositories;

import com.example.desarrollodeaplicaciones.models.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<People, Long> {}
